package com.app.infra.application.mapper.restaurant;

import com.app.core.domain.location.Location;
import com.app.core.domain.restaurant.Restaurant;
import com.app.infra.application.dto.restaurant.RestaurantDTO;
import com.app.infra.application.mapper.location.LocationMapper;
import com.app.infra.application.mapper.user.UserMapper;
import com.app.infra.application.request.restaurant.RestaurantRequest;
import com.app.infra.entity.location.LocationEntity;
import com.app.infra.entity.restaurant.RestaurantEntity;
import com.app.infra.entity.user.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    private final LocationMapper locationMapper;

    private final UserMapper userMapper;

    public RestaurantMapper(final LocationMapper locationMapper, final UserMapper userMapper) {
        this.locationMapper = locationMapper;
        this.userMapper = userMapper;
    }

    public Restaurant mapToRestaurant(@NotNull final RestaurantRequest request, @NotNull final Location location) {

        return new Restaurant(
                request.getName(),
                location,
                request.getKitchenType(),
                request.getRestaurantOwner()
        );
    }

    public Restaurant mapToRestaurant(@NotNull final RestaurantRequest request, @NotNull final Long id) {
        return new Restaurant();
    }

    public Restaurant mapToRestaurant(@NotNull final RestaurantRequest request) {
        return new Restaurant();
    }

    public Restaurant mapToRestaurant(@NotNull final RestaurantEntity entity) {
        return new Restaurant(
                entity.getId(),
                entity.getName(),
                locationMapper.mapToLocation(entity.getLocation()),
                entity.getKitchenType(),
                userMapper.toUserEntityWithoutRole(entity.getRestaurantOwner())
        );
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
        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getLocation(),
                restaurant.getKitchenType(),
                restaurant.getRestaurantOwner()
        );
    }


}
