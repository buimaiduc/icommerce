package com.icommerce.app.shopping.product.service.service;

import com.icommerce.app.shopping.product.service.exception.ResourceNotFoundException;
import com.icommerce.app.shopping.product.service.model.Brand;
import com.icommerce.app.shopping.product.service.model.Product;
import com.icommerce.app.shopping.product.service.repository.ProductRepository;
import com.icommerce.app.shopping.product.service.rest.request.ProductRequest;
import com.icommerce.app.shopping.product.service.rest.request.UpdateProductRequest;
import com.icommerce.app.shopping.product.service.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductPriceTrackingService productPriceTrackingService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AuditLogService auditLogService;
    @Mock
    private BrandService brandService;

    @Test(expected = ResourceNotFoundException.class)
    public void insertWithoutBrand() {
        ProductRequest productRequest = new ProductRequest();
        Assert.assertNotNull(productService.insert(productRequest));
    }

    @Test
    public void insert() {
        Brand brand = new Brand();
        brand.setName("Brand");

        ProductRequest productRequest = new ProductRequest();
        when(productRepository.save(anyObject())).thenReturn(new Product());
        when(brandService.findById(anyObject())).thenReturn(Optional.of(brand));
        Assert.assertNotNull(productService.insert(productRequest));
    }


    @Test
    public void findById() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product()));
        Assert.assertNotNull(productService.findById(12313L));
    }

    @Test
    public void update() {
        Product product = new Product();
        product.setDeleted(false);
        product.setPrice(100.0);

        Product updatedProduct = new Product();
        updatedProduct.setDeleted(false);
        updatedProduct.setPrice(200.0);

        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setPrice(200.0);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(anyObject())).thenReturn(updatedProduct);
        Assert.assertEquals(productService.update(12313L, updateProductRequest).getPrice(), Double.valueOf(200.0));
    }

    @Test
    public void delete() {
        Product product = new Product();
        product.setDeleted(false);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        Assert.assertTrue(productService.delete(12313L));
    }

    @Test
    public void findByIds() {
        List<Product> products = Arrays.asList(new Product());
        when(productRepository.findAllById(anyList())).thenReturn(products);
        Assert.assertNotNull(productService.findByIds(Arrays.asList(12313L, 12314L)));
        Assert.assertNotNull(products.get(0));
    }
}
