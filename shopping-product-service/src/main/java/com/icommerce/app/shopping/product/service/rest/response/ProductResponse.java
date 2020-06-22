package com.icommerce.app.shopping.product.service.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductResponse {
    private Long id;
    private String name;
    private Double price;
    @JsonProperty("brand")
    private BrandResponse brand;
    private String colour;
    private String image;

    public ProductResponse() {
        // default constructor
    }

    public ProductResponse(Long id, String name, Double price, BrandResponse brand, String colour, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.colour = colour;
        this.image = image;
    }

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

    public BrandResponse getBrand() {
        return brand;
    }

    public void setBrand(BrandResponse brand) {
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
