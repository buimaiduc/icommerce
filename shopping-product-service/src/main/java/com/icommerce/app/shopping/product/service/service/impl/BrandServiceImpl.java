package com.icommerce.app.shopping.product.service.service.impl;

import com.icommerce.app.shopping.product.service.model.Brand;
import com.icommerce.app.shopping.product.service.repository.BrandRepository;
import com.icommerce.app.shopping.product.service.rest.request.BrandRequest;
import com.icommerce.app.shopping.product.service.service.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand insert(BrandRequest brandRequest) {
        return brandRepository.save(this.toBrand(brandRequest));
    }

    @Override
    public List<Brand> getBrands() {
        final List<Brand> brands = new ArrayList<>();
        brandRepository.findAll().forEach(brands::add);

        return brands;
    }

    @Override
    public Optional<Brand> findById(Long id) {
        LOGGER.debug("BrandService - findById: {}", id);
        return brandRepository.findById(id);
    }

    private Brand toBrand(final BrandRequest brandRequest) {
        Brand brand = new Brand();
        brand.setName(brandRequest.getName());
        brand.setCreatedDate(new Date());
        brand.setDeleted(false);

        return brand;
    }
}
