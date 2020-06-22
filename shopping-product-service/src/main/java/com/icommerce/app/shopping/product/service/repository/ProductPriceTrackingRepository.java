package com.icommerce.app.shopping.product.service.repository;

import com.icommerce.app.shopping.product.service.model.ProductPriceTracking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPriceTrackingRepository extends CrudRepository<ProductPriceTracking, Long> {
    List<ProductPriceTracking> findByProductId(Long productId);
}
