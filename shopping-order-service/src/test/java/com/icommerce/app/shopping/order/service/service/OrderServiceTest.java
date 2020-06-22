package com.icommerce.app.shopping.order.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.app.shopping.order.service.model.Order;
import com.icommerce.app.shopping.order.service.repository.OrderRepository;
import com.icommerce.app.shopping.order.service.rest.request.OrderDetailRequest;
import com.icommerce.app.shopping.order.service.rest.request.OrderRequest;
import com.icommerce.app.shopping.order.service.service.api.ProductApi;
import com.icommerce.app.shopping.order.service.service.api.proxy.model.ProductProxy;
import com.icommerce.app.shopping.order.service.service.api.proxy.model.ProductProxyList;
import com.icommerce.app.shopping.order.service.service.impl.OrderServiceImpl;
import feign.Request;
import feign.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;

import java.nio.charset.Charset;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    ProductApi productApi;
    @Mock
    private ObjectMapper mapper;

    @Test
    public void insert() throws JsonProcessingException {
        OrderRequest orderRequest = new OrderRequest();
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setProductId(1L);
        orderDetailRequest.setPrice(100.0);
        orderRequest.setOrderDetailRequests(Arrays.asList(orderDetailRequest));

        ProductProxyList productProxyList = new ProductProxyList();
        ProductProxy productProxy = new ProductProxy();
        productProxy.setId(1L);
        productProxy.setPrice(100.0);
        productProxyList.setProductProxies(Arrays.asList(productProxy));

        Map<String, Collection<String>> headers = new HashMap<>();
        Response.Builder builder = Response.builder();
        headers.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("application/json"));
        builder.request(Request.create(Request.HttpMethod.GET, "http", headers, "test".getBytes(), Charset.defaultCharset()));
        String body = "[{\n" +
                "\"id\": \"1\",\n" +
                "\"name\": \"Shoe\",\n" +
                "\"price\": \"100.0\"\n" +
                "}]";
        Response response = builder.body(body, Charset.defaultCharset()).headers(headers).status(200).build();
        when(mapper.readValue(body, ProductProxyList.class)).thenReturn(productProxyList);
        when(productApi.getProducts(anyList())).thenReturn(response);
        when(orderRepository.save(anyObject())).thenReturn(new Order());
        Assert.assertNotNull(orderService.insert(12313L, orderRequest));
    }

    @Test
    public void findById() {
        when(orderRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(new Order());
        Assert.assertNotNull(orderService.findById(12313L, 12314L));
    }
}