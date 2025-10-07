package com.app.infra.entity.restaurant;

import com.app.infra.entity.base.BaseEntity;
import com.app.infra.entity.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "res_restaurants")
public class RestaurantEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    //TODO: Implementar location
    private String location;

    //TODO: Validar se vai ser enum ou entity
    private String kitchenType;

    @JoinColumn(name = "restaurant_owner_usr_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity restaurantOwner;

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

    public UserEntity getRestaurantOwner() {
        return restaurantOwner;
    }

    public void setRestaurantOwner(UserEntity restaurantOwner) {
        this.restaurantOwner = restaurantOwner;
    }

    public RestaurantEntity() {
    }
}
