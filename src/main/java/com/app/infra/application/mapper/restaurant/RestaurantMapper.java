package com.app.infra.application.mapper.restaurant;

import com.app.core.domain.location.Location;
import com.app.core.domain.restaurant.Restaurant;
import com.app.infra.application.dto.restaurant.RestaurantDTO;
import com.app.infra.application.request.restaurant.RestaurantRequest;
import com.app.infra.entity.location.LocationEntity;
import com.app.infra.entity.restaurant.RestaurantEntity;
import com.app.infra.entity.user.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public Restaurant mapToRestaurant(@NotNull final RestaurantRequest json, @NotNull final Location location) {

        return new Restaurant(
                json.getName(),
                location,
                json.getKitchenType(),
                json.getRestaurantOwner()
        );
    }

    public Restaurant mapToRestaurant(@NotNull final RestaurantRequest json, @NotNull final Long id) {
        return new Restaurant();
    }

    public Restaurant mapToRestaurant(@NotNull final RestaurantEntity entity) {
        return new Restaurant();
    }

    public RestaurantEntity toEntity(@NotNull final Restaurant restaurant) {
        return new RestaurantEntity();
    }

    public RestaurantEntity toEntity(@NotNull final Restaurant restaurant, @NotNull final LocationEntity location, @NotNull final UserEntity user) {
        return new RestaurantEntity(
                restaurant.getId(),
                restaurant.getName(),
                location,
                restaurant.getKitchenType(),
                user
        );
    }

    public RestaurantDTO mapToDTO(@NotNull final Restaurant restaurant) {
        return new RestaurantDTO();
    }


}
