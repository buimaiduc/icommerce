package com.icommerce.app.shopping.product.service.service;

import com.icommerce.app.shopping.product.service.model.ProductPriceTracking;
import com.icommerce.app.shopping.product.service.repository.ProductPriceTrackingRepository;
import com.icommerce.app.shopping.product.service.service.impl.ProductPriceTrackingServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductPriceTrackingServiceTest {
    @InjectMocks
    private ProductPriceTrackingServiceImpl productPriceTrackingService;
    @Mock
    private ProductPriceTrackingRepository productPriceTrackingRepository;

    @Test
    public void insert() {
        ProductPriceTracking productPriceTracking = new ProductPriceTracking();
        when(productPriceTrackingRepository.save(anyObject())).thenReturn(productPriceTracking);
        Assert.assertNotNull(productPriceTrackingService.insert(productPriceTracking));
    }

    @Test
    public void findByProductId() {
        List<ProductPriceTracking> productPriceTrackings = new ArrayList<>();
        productPriceTrackings.add(new ProductPriceTracking());
        productPriceTrackings.add(new ProductPriceTracking());
        when(productPriceTrackingRepository.findByProductId(anyLong())).thenReturn(productPriceTrackings);
        Assert.assertNotNull(productPriceTrackingService.findByProductId(12313L));
    }
}
