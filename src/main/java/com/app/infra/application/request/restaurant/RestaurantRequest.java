package com.app.infra.application.request.restaurant;

import com.app.infra.application.request.location.LocationRequest;
import com.app.infra.application.request.user.UserRequest;

public class RestaurantRequest {

    private Long id;

    private String name;

    private LocationRequest location;

    private String kitchenType;

    private UserRequest restaurantOwner;

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

    public UserRequest getRestaurantOwner() {
        return restaurantOwner;
    }

    public void setRestaurantOwner(UserRequest restaurantOwner) {
        this.restaurantOwner = restaurantOwner;
    }

    public RestaurantRequest() {
    }
}
