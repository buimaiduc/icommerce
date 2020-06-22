package com.icommerce.app.shopping.product.service.service;

import com.icommerce.app.shopping.product.service.model.Product;
import com.icommerce.app.shopping.product.service.rest.request.ProductRequest;
import com.icommerce.app.shopping.product.service.rest.request.SearchRequest;
import com.icommerce.app.shopping.product.service.rest.request.UpdateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<Product> searchProducts(SearchRequest crit);

    Product insert(ProductRequest productRequest);

    Product findById(Long id);

    Product update(Long id, UpdateProductRequest updateProductRequest);

    Boolean delete(Long id);

    List<Product> findByIds(List<Long> ids);
}
