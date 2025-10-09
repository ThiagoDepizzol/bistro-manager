package com.app.core.domain.restaurant;

import com.app.core.domain.base.BaseEntity;
import com.app.core.domain.location.Location;
import com.app.core.domain.user.User;

public class Restaurant extends BaseEntity {

    private Long id;

    private String name;

    private Location location;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
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

    public Restaurant() {
    }

}
