package com.app.core.usecases.restaurant;

import com.app.core.domain.restaurant.Restaurant;
import com.app.core.gateways.restaurant.RestaurantGateway;

import java.util.List;
import java.util.Optional;

public class RestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public RestaurantUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public Restaurant created(final Restaurant restaurant) {

        return restaurantGateway.created(restaurant);
    }

    public Restaurant update(final Restaurant restaurant) {

        return restaurantGateway.update(restaurant);
    }

    public Optional<Restaurant> findById(final Long id) {

        return restaurantGateway.findById(id);
    }

    public List<Restaurant> findAllActive(final int page, final int size) {

        return restaurantGateway.findAllActive(page, size);
    }
}
