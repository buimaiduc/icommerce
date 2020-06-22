package com.icommerce.app.shopping.product.service.service;

import com.icommerce.app.shopping.product.service.model.Brand;
import com.icommerce.app.shopping.product.service.repository.BrandRepository;
import com.icommerce.app.shopping.product.service.rest.request.BrandRequest;
import com.icommerce.app.shopping.product.service.service.impl.BrandServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BrandServiceTest {

    @InjectMocks
    private BrandServiceImpl brandService;
    @Mock
    private BrandRepository brandRepository;

    @Test
    public void insert() {
        BrandRequest brandRequest = new BrandRequest();
        when(brandRepository.save(anyObject())).thenReturn(new Brand());
        Assert.assertNotNull(brandService.insert(brandRequest));
    }

    @Test
    public void getBrands() {
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand());
        brands.add(new Brand());
        when(brandRepository.findAll()).thenReturn(brands);
        Assert.assertNotNull(brandService.getBrands());
    }

    @Test
    public void findById() {
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(new Brand()));
        Assert.assertNotNull(brandRepository.findById(12313L));
    }
}