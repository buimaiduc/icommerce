package com.icommerce.app.shopping.order.service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.app.shopping.common.rest.exception.ApiError;
import com.icommerce.app.shopping.common.rest.exception.ApiException;
import com.icommerce.app.shopping.common.rest.exception.ResourceNotFoundException;
import com.icommerce.app.shopping.order.service.model.Order;
import com.icommerce.app.shopping.order.service.model.OrderDetail;
import com.icommerce.app.shopping.order.service.repository.OrderRepository;
import com.icommerce.app.shopping.order.service.rest.request.OrderRequest;
import com.icommerce.app.shopping.order.service.service.OrderService;
import com.icommerce.app.shopping.order.service.service.api.ProductApi;
import com.icommerce.app.shopping.order.service.service.api.proxy.model.ProductProxy;
import com.icommerce.app.shopping.order.service.service.api.proxy.model.ProductProxyList;
import feign.Response;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RefreshScope
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductApi productApi;

    @Autowired
    private ObjectMapper mapper;

    @Value("${app.allowed.test}")
    private String test;

    @Override
    public Order insert(Long userId, OrderRequest orderRequest) {
        Order order = this.toOrder(orderRequest);
        order.setUserId(userId);
        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long id, Long userId) {
        return Optional.ofNullable(orderRepository.findByIdAndUserId(id, userId))
                .orElseThrow(() -> new ResourceNotFoundException("Order " + id + " of user " + userId + " not found"));
    }

    private Order toOrder(OrderRequest orderRequest) {
        Order order = new Order();

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.addAll(orderRequest.getOrderDetailRequests().stream()
                .map(orderDetail -> new OrderDetail(orderDetail.getProductId(), orderDetail.getPrice()))
                .collect(Collectors.toList())
        );

        order.setOrderDetails(orderDetails);
        order.setCreatedDate(new Date());

        this.validateOrderDetail(orderDetails);
        order.setOrderDetails(orderDetails);

        return order;
    }

    private void validateOrderDetail(List<OrderDetail> orderDetails) {
        final Map<Long, OrderDetail> orderDetailMap = orderDetails.stream().collect(Collectors.toMap(OrderDetail::getProductId, Function.identity()));

        if (MapUtils.isEmpty(orderDetailMap)) {
            throw new ResourceNotFoundException("Products not found");
        }

        try {
            List<Long> productIds = new ArrayList<>(orderDetailMap.keySet());
            Response response = productApi.getProducts(productIds);
            if (response.status() >= 400) {
                throw new ResourceNotFoundException("Products " + test + " not found");
            }

            ProductProxyList productList = mapper.readValue(IOUtils.toString(response.body().asInputStream()), ProductProxyList.class);
            final Map<Long, ProductProxy> productListMap = productList.getProductProxies().stream().collect(Collectors.toMap(ProductProxy::getId, Function.identity()));

            orderDetailMap.forEach((id, orderDetail) -> {
                if (!productListMap.containsKey(id)) {
                    throw new ResourceNotFoundException("Products " + test + " not found");
                }
            });
        } catch (IOException ex) {
            throw new ApiException(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ""));
        }
    }
}