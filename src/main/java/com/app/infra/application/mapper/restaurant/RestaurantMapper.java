package com.app.infra.application.mapper.restaurant;

import com.app.core.domain.restaurant.Restaurant;
import com.app.infra.application.dto.restaurant.RestaurantDTO;
import com.app.infra.application.mapper.location.LocationMapper;
import com.app.infra.application.mapper.user.UserMapper;
import com.app.infra.application.request.restaurant.RestaurantRequest;
import com.app.infra.entity.restaurant.RestaurantEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RestaurantMapper {

    private final LocationMapper locationMapper;

    private final UserMapper userMapper;

    public RestaurantMapper(final LocationMapper locationMapper, final UserMapper userMapper) {
        this.locationMapper = locationMapper;
        this.userMapper = userMapper;
    }

    public Restaurant map(@NotNull final RestaurantRequest request) {

        final Restaurant restaurant = new Restaurant();

        restaurant.setId(request.getId());
        restaurant.setName(request.getName());
        restaurant.setKitchenType(request.getKitchenType());

        if (Objects.nonNull(request.getLocation())) {

            restaurant.setLocation(locationMapper.map(request.getLocation()));

        }

        if (Objects.nonNull(request.getRestaurantOwner())) {

            restaurant.setRestaurantOwner(userMapper.mapWithoutEncryptPassword(request.getRestaurantOwner()));

        }

        return restaurant;
    }

    public Restaurant map(@NotNull final RestaurantRequest request, @NotNull final Long id) {

        final Restaurant restaurant = new Restaurant();

        restaurant.setId(id);
        restaurant.setName(request.getName());
        restaurant.setKitchenType(request.getKitchenType());

        if (Objects.nonNull(request.getLocation())) {

            restaurant.setLocation(locationMapper.map(request.getLocation()));

        }

        if (Objects.nonNull(request.getRestaurantOwner())) {

            restaurant.setRestaurantOwner(userMapper.mapWithoutEncryptPassword(request.getRestaurantOwner()));

        }

        return restaurant;
    }

    public Restaurant map(@NotNull final RestaurantEntity entity) {

        final Restaurant restaurant = new Restaurant();

        restaurant.setId(entity.getId());
        restaurant.setName(entity.getName());
        restaurant.setKitchenType(entity.getKitchenType());

        if (Objects.nonNull(entity.getLocation())) {

            restaurant.setLocation(locationMapper.map(entity.getLocation()));

        }

        if (Objects.nonNull(entity.getRestaurantOwner())) {

            restaurant.setRestaurantOwner(userMapper.map(entity.getRestaurantOwner()));

        }

        return restaurant;
    }

    public RestaurantEntity toEntity(@NotNull final Restaurant restaurant) {

        final RestaurantEntity entity = new RestaurantEntity();


        entity.setId(restaurant.getId());
        entity.setName(restaurant.getName());
        entity.setKitchenType(restaurant.getKitchenType());

        if (Objects.nonNull(restaurant.getLocation())) {

            entity.setLocation(locationMapper.toEntity(restaurant.getLocation()));

        }

        if (Objects.nonNull(restaurant.getRestaurantOwner())) {

            entity.setRestaurantOwner(userMapper.toEntity(restaurant.getRestaurantOwner()));

        }

        return entity;
    }

    public RestaurantDTO toDTO(@NotNull final Restaurant restaurant) {

        final RestaurantDTO dto = new RestaurantDTO();

        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setKitchenType(restaurant.getKitchenType());

        if (Objects.nonNull(restaurant.getLocation())) {

            dto.setLocation(locationMapper.toDTO(restaurant.getLocation()));

        }

        if (Objects.nonNull(restaurant.getRestaurantOwner())) {

            dto.setRestaurantOwner(userMapper.toDTO(restaurant.getRestaurantOwner()));

        }

        return dto;
    }


}
