package com.app.core.domain.restaurant;

import java.math.BigDecimal;

public class Menu {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Restaurant restaurant;

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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Menu() {
    }

    public Menu(Long id, String name, String description, BigDecimal price, Restaurant restaurant, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
        this.image = image;
    }

    public Menu(String name, String description, BigDecimal price, Restaurant restaurant, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
        this.image = image;
    }
}
