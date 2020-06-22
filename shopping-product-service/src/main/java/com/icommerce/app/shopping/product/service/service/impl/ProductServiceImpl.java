package com.icommerce.app.shopping.product.service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.app.shopping.common.event.message.AuditLogType;
import com.icommerce.app.shopping.product.service.exception.ResourceNotFoundException;
import com.icommerce.app.shopping.product.service.model.Brand;
import com.icommerce.app.shopping.product.service.model.Product;
import com.icommerce.app.shopping.product.service.model.ProductPriceTracking;
import com.icommerce.app.shopping.product.service.repository.GenericSpesification;
import com.icommerce.app.shopping.product.service.repository.ProductRepository;
import com.icommerce.app.shopping.product.service.repository.SearchCriteria;
import com.icommerce.app.shopping.product.service.repository.SearchOperation;
import com.icommerce.app.shopping.product.service.rest.request.ProductRequest;
import com.icommerce.app.shopping.product.service.rest.request.SearchRequest;
import com.icommerce.app.shopping.product.service.rest.request.UpdateProductRequest;
import com.icommerce.app.shopping.product.service.service.AuditLogService;
import com.icommerce.app.shopping.product.service.service.BrandService;
import com.icommerce.app.shopping.product.service.service.ProductPriceTrackingService;
import com.icommerce.app.shopping.product.service.service.ProductService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandServiceImpl.class);

    private List<String> searchFields = Arrays.asList("name", "colour");

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandService brandService;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private ProductPriceTrackingService productPriceTrackingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Product insert(ProductRequest productRequest) {
        return productRepository.save(this.toProduct(productRequest));
    }

    @Override
    public Product findById(Long id) {
        LOGGER.debug("ProductService - findById: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));

        auditLogService.log(Long.valueOf(-1), id, AuditLogType.PRODUCT_VIEW);
        return product;
    }

    @Override
    public Product update(Long id, UpdateProductRequest updateProductRequest) {
        LOGGER.debug("ProductService - update product: {} with price: ", updateProductRequest.getPrice());
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));

        product.setPrice(updateProductRequest.getPrice());
        this.savePriceTracking(product, updateProductRequest.getPrice());
        return productRepository.save(product);
    }

    @Override
    public Boolean delete(Long id) {
        LOGGER.debug("ProductService - delete product: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));

        product.setDeleted(true);
        try {
            productRepository.save(product);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public List<Product> findByIds(List<Long> ids) {
        LOGGER.debug("ProductService - findByIds: {}", ids);
        return Optional.of(productRepository.findAllById(ids)).orElse(Collections.EMPTY_LIST);
    }

    @Override
    public Page<Product> searchProducts(SearchRequest crit) {
        GenericSpesification genericSpesification = new GenericSpesification<Product>();

        crit.getFilterFields().getEqualCriteria().forEach((key, value) -> {
            genericSpesification.add(this.buildSearchCriteria(key, value, SearchOperation.EQUAL));
        });

        crit.getFilterFields().getNotEqualCriteria().forEach((key, value) -> {
            genericSpesification.add(this.buildSearchCriteria(key, value, SearchOperation.NOT_EQUAL));
        });

        crit.getFilterFields().getLikeCriteria().forEach((key, value) -> {
            genericSpesification.add(this.buildSearchCriteria(key, value, SearchOperation.MATCH));
        });

        crit.getFilterFields().getInCriteria().forEach((key, value) -> {
            genericSpesification.add(this.buildSearchCriteria(key, value, SearchOperation.IN));
        });

        crit.getFilterFields().getNotInCriteria().forEach((key, value) -> {
            genericSpesification.add(this.buildSearchCriteria(key, value, SearchOperation.NOT_IN));
        });

        if (StringUtils.isNotEmpty(crit.getSearchValue()) && CollectionUtils.isNotEmpty(searchFields)) {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setKeyValuePair(searchFields.stream()
                    .collect(Collectors.toMap(Function.identity(), value -> crit.getSearchValue())));
            genericSpesification.add(searchCriteria);
        }

        Page<Product> productPage = productRepository.findAll(genericSpesification, this.buildPageSort(crit));

        try {
            auditLogService.log(Long.valueOf(-1), objectMapper.writeValueAsString(crit), AuditLogType.PRODUCT_SEARCH);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return productPage;
    }

    private SearchCriteria buildSearchCriteria(String key, Object value, SearchOperation operation) {
        if (value instanceof List) {
            return new SearchCriteria(key, (List) value, operation);
        } else {
            return new SearchCriteria(key, value, operation);
        }
    }

    private void savePriceTracking(Product product, Double price) {
        Runnable priceTrackingTask = () -> {
            productPriceTrackingService.insert(new ProductPriceTracking(price, product, new Date()));
        };

        Thread priceTrackingThread = new Thread(priceTrackingTask);
        priceTrackingThread.start();
    }

    private Product toProduct(ProductRequest productRequest) {
        Brand brand = brandService.findById(productRequest.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand " + productRequest.getBrandId() + " not found"));

        final Product product = new Product();
        product.setName(productRequest.getName());
        product.setBrand(brand);
        product.setColour(productRequest.getColour());
        product.setPrice(productRequest.getPrice());
        product.setImage(productRequest.getImage());

        return product;
    }

    private PageRequest buildPageSort(SearchRequest searchRequest) {
        if (Objects.isNull(searchRequest.getSortRequest()) || StringUtils.isEmpty(searchRequest.getSortRequest().getSortField())) {
            return PageRequest.of(searchRequest.getPage(), searchRequest.getSize());
        }

        Sort.Direction sortDirection = Sort.Direction.
                fromOptionalString(searchRequest.getSortRequest().getSortDirection().toString()).orElse(Sort.Direction.DESC);

        return PageRequest.of(searchRequest.getPage(), searchRequest.getSize(),
                Sort.by(sortDirection, searchRequest.getSortRequest().getSortField()));

    }
}