package com.icommerce.app.shopping.product.service.service.impl;

import com.icommerce.app.shopping.product.service.model.ProductPriceTracking;
import com.icommerce.app.shopping.product.service.repository.ProductPriceTrackingRepository;
import com.icommerce.app.shopping.product.service.service.ProductPriceTrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPriceTrackingServiceImpl implements ProductPriceTrackingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductPriceTrackingServiceImpl.class);

    @Autowired
    private ProductPriceTrackingRepository productPriceTrackingRepository;

    @Override
    public ProductPriceTracking insert(ProductPriceTracking productPriceTracking) {
        return productPriceTrackingRepository.save(productPriceTracking);
    }

    @Override
    public List<ProductPriceTracking> findByProductId(Long productId) {
        LOGGER.debug("ProductPriceTrackingService - findByProductId: {}", productId);
        return productPriceTrackingRepository.findByProductId(productId);
    }
}
