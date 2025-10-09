package com.app.core.gateways.restaurant;

import com.app.core.domain.restaurant.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantGateway {

    Restaurant created(Restaurant restaurant);

    Restaurant update(Restaurant restaurant);

    Optional<Restaurant> findById(Long id);

    List<Restaurant> findAllActive(int page, int size);
}
