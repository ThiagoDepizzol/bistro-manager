package com.app.infra.application.request.restaurant;

import com.app.core.domain.user.User;
import com.app.infra.application.request.location.LocationRequest;

public class RestaurantRequest {

    private Long id;

    private String name;

    private LocationRequest location;

    private String kitchenType;

    private User restaurantOwner;

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

    public LocationRequest getLocation() {
        return location;
    }

    public void setLocation(LocationRequest location) {
        this.location = location;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }

    public User getRestaurantOwner() {
        return restaurantOwner;
    }

    public void setRestaurantOwner(User restaurantOwner) {
        this.restaurantOwner = restaurantOwner;
    }
}
