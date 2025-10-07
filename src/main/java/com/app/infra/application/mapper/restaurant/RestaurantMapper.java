package com.app.infra.application.mapper.restaurant;

import com.app.core.domain.restaurant.Restaurant;
import com.app.infra.application.dto.restaurant.RestaurantDTO;
import com.app.infra.application.request.restaurant.RestaurantRequest;
import com.app.infra.entity.restaurant.RestaurantEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public Restaurant mapToRestaurant(@NotNull final RestaurantRequest json) {
        return new Restaurant();
    }

    public Restaurant mapToRestaurant(@NotNull final RestaurantRequest json, @NotNull final Long id) {
        return new Restaurant();
    }

    public Restaurant mapToRestaurant(@NotNull final RestaurantEntity entity) {
        return new Restaurant();
    }

    public RestaurantEntity toEntity(@NotNull final Restaurant user) {
        return new RestaurantEntity();
    }

    public RestaurantDTO mapToDTO(@NotNull final Restaurant restaurant) {
        return new RestaurantDTO();
    }


}
