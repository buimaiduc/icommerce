package com.icommerce.app.shopping.order.service.rest.request;

import java.util.List;

public class OrderRequest {
    private List<OrderDetailRequest> orderDetailRequests;

    public List<OrderDetailRequest> getOrderDetailRequests() {
        return orderDetailRequests;
    }

    public void setOrderDetailRequests(List<OrderDetailRequest> orderDetailRequests) {
        this.orderDetailRequests = orderDetailRequests;
    }
}
