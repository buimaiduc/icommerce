package com.icommerce.app.shopping.product.service.service;

import com.icommerce.app.shopping.product.service.model.Brand;
import com.icommerce.app.shopping.product.service.rest.request.BrandRequest;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Brand insert(BrandRequest brandRequest);
    List<Brand> getBrands();
    Optional<Brand> findById(Long id);
}
