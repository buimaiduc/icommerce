package com.icommerce.app.shopping.product.service.repository;

import com.icommerce.app.shopping.product.service.model.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Long> {
}
