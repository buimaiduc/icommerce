package com.icommerce.app.shopping.order.service.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SHOPPING_ORDER_DETAIL")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = -8421189687045445666L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "PRODUCT_ID", updatable = false, nullable = false)
    private Long productId;

    @Column(name = "TOTAL")
    private Double price;

    public OrderDetail() {
        // default constructor
    }

    public OrderDetail(Long productId, Double price) {
        this.productId = productId;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
