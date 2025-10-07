package com.app.core.domain.restaurant;

import com.app.core.domain.base.BaseEntity;
import com.app.core.domain.user.User;

public class Restaurant extends BaseEntity {

    private Long id;

    private String name;

    // TODO: Implementar entity location
    private String location;

    //TODO: Validar se vai ser enum ou entity
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
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
