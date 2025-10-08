package com.app.infra.application.dto.restaurant;

import java.math.BigDecimal;

public class MenuDTO {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private RestaurantDTO restaurant;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MenuDTO(Long id, String name, String description, BigDecimal price, RestaurantDTO restaurant, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
        this.image = image;
    }
}
