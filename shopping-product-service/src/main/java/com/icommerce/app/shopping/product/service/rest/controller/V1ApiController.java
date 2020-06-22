package com.icommerce.app.shopping.product.service.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.app.shopping.product.service.model.Brand;
import com.icommerce.app.shopping.product.service.model.Product;
import com.icommerce.app.shopping.product.service.model.ProductPriceTracking;
import com.icommerce.app.shopping.product.service.rest.request.BrandRequest;
import com.icommerce.app.shopping.product.service.rest.request.ProductRequest;
import com.icommerce.app.shopping.product.service.rest.request.SearchRequest;
import com.icommerce.app.shopping.product.service.rest.request.UpdateProductRequest;
import com.icommerce.app.shopping.product.service.rest.response.BrandResponse;
import com.icommerce.app.shopping.product.service.rest.response.ProductListResponse;
import com.icommerce.app.shopping.product.service.rest.response.ProductPriceTrackingResponse;
import com.icommerce.app.shopping.product.service.rest.response.ProductResponse;
import com.icommerce.app.shopping.product.service.service.BrandService;
import com.icommerce.app.shopping.product.service.service.ProductPriceTrackingService;
import com.icommerce.app.shopping.product.service.service.ProductService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class V1ApiController implements V1Api {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductPriceTrackingService productPriceTrackingService;

    @Override
    public ResponseEntity<String> insertProduct(@Valid ProductRequest productRequest) throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(productService.insert(productRequest)));
    }

    @Override
    public ResponseEntity<String> searchProducts(@Valid SearchRequest searchRequest) throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(productService.searchProducts(searchRequest).get().collect(Collectors.toList())));
    }

    @Override
    public ResponseEntity<String> getProducts(List<Long> ids) throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(this.buildProductList(productService.findByIds(ids))));
    }

    @Override
    public ResponseEntity<String> getProduct(Long id) throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(productService.findById(id)));
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        if (!productService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateProduct(Long id, @Valid UpdateProductRequest updateProductRequest) throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(productService.update(id, updateProductRequest)));
    }

    @Override
    public ResponseEntity<String> insertBrand(@Valid BrandRequest brandRequest) throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(brandService.insert(brandRequest)));
    }

    @Override
    public ResponseEntity<String> getBrands() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(brandService.getBrands()));
    }

    @Override
    public ResponseEntity<String> getProductPrices(Long productId) throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(
                this.buildProductPriceTrackingResponse(productId, productPriceTrackingService.findByProductId(productId))));
    }

    private ProductPriceTrackingResponse buildProductPriceTrackingResponse(Long productId,
                                                                           List<ProductPriceTracking> productPriceTrackings) {
        final ProductPriceTrackingResponse productPriceTrackingResponse = new ProductPriceTrackingResponse();
        if (CollectionUtils.isEmpty(productPriceTrackings)) {
            return productPriceTrackingResponse;
        }

        productPriceTrackingResponse.setPrices(
                productPriceTrackings.stream().map(ProductPriceTracking::getPrice).collect(Collectors.toList()));
        productPriceTrackingResponse.setId(productId);

        return productPriceTrackingResponse;
    }

    private ProductListResponse buildProductList(List<Product> products) {
        ProductListResponse productList = new ProductListResponse();
        List<ProductResponse> productResponses = products.stream().map(product -> {
            final Brand brand = product.getBrand();

            BrandResponse brandResponse = new BrandResponse(brand.getId(), brand.getName(), brand.getDeleted(), brand.getCreatedDate());
            return new ProductResponse(product.getId(), product.getName(), product.getPrice(), brandResponse, product.getColour(), product.getImage());
        }).collect(Collectors.toList());
        productList.setProductResponses(productResponses);

        return productList;
    }

}