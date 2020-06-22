package com.icommerce.app.shopping.order.service.service.api.proxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductProxy {

    private Long id;
    private String name;
    private Double price;
    @JsonProperty("brand")
    private BrandProxy brand;
    private String colour;
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BrandProxy getBrand() {
        return brand;
    }

    public void setBrand(BrandProxy brand) {
        this.brand = brand;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
