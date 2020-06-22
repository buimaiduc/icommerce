package com.icommerce.app.shopping.order.service.service;

import com.icommerce.app.shopping.order.service.model.Order;
import com.icommerce.app.shopping.order.service.rest.request.OrderRequest;

public interface OrderService {

    Order insert(Long userId, OrderRequest orderRequest);

    Order findById(Long id, Long userId);
}
