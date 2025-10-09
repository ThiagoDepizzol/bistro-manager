package com.app.infra.application.dto.restaurant;

import com.app.infra.application.dto.location.LocationDTO;
import com.app.infra.application.dto.user.UserDTO;

public class RestaurantDTO {
    private Long id;

    private String name;

    private LocationDTO location;

    private String kitchenType;

    private UserDTO restaurantOwner;

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

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }

    public UserDTO getRestaurantOwner() {
        return restaurantOwner;
    }

    public void setRestaurantOwner(UserDTO restaurantOwner) {
        this.restaurantOwner = restaurantOwner;
    }

    public RestaurantDTO() {
    }
}
