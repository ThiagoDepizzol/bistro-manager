package com.app.infra.gateway.restaurant;

import com.app.core.domain.restaurant.Restaurant;
import com.app.core.gateways.restaurant.RestaurantGateway;
import com.app.infra.controller.restaurant.mapper.RestaurantMapper;
import com.app.infra.entity.restaurant.RestaurantEntity;
import com.app.infra.repository.restaurant.RestaurantRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RestaurantRepositoryGateway implements RestaurantGateway {

    private final RestaurantMapper restaurantMapper;

    private final RestaurantRepository restaurantRepository;

    public RestaurantRepositoryGateway(final RestaurantMapper restaurantMapper, final RestaurantRepository restaurantRepository) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant save(@NotNull final Restaurant restaurant) {

        final RestaurantEntity restaurantEntity = restaurantMapper.toEntity(restaurant);

        final RestaurantEntity savedEntity = restaurantRepository.save(restaurantEntity);

        return restaurantMapper.mapToRestaurant(savedEntity);
    }

    @Override
    public Optional<Restaurant> findById(@NotNull final Long id) {

        final RestaurantEntity savedEntity = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Restaurant not found"));

        final Restaurant restaurant = restaurantMapper.mapToRestaurant(savedEntity);

        return Optional.of(restaurant);
    }

    @Override
    public List<Restaurant> findAllActive(final int page, final int size) {

        final Pageable pageable = PageRequest.of(page, size);

        final Page<RestaurantEntity> entities = restaurantRepository.findAllByActive(pageable);

        return entities
                .stream()
                .map(restaurantMapper::mapToRestaurant)
                .collect(Collectors.toList());
    }
}
