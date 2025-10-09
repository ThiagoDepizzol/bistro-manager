package com.app.infra.gateway.restaurant;

import com.app.core.domain.location.Location;
import com.app.core.domain.restaurant.Restaurant;
import com.app.core.gateways.restaurant.RestaurantGateway;
import com.app.core.usecases.location.LocationUseCase;
import com.app.infra.application.mapper.restaurant.RestaurantMapper;
import com.app.infra.entity.restaurant.RestaurantEntity;
import com.app.infra.repository.restaurant.RestaurantRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RestaurantRepositoryGateway implements RestaurantGateway {

    private final RestaurantMapper restaurantMapper;

    private final RestaurantRepository restaurantRepository;

    private final LocationUseCase locationUseCase;

    public RestaurantRepositoryGateway(final RestaurantMapper restaurantMapper, final RestaurantRepository restaurantRepository, final LocationUseCase locationUseCase) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
        this.locationUseCase = locationUseCase;
    }

    @Override
    public Restaurant created(@NotNull final Restaurant restaurant) {

        final Location replaceLocation = locationUseCase.getOneByZipCodeOrCreated(restaurant.getLocation());

        restaurant.setLocation(replaceLocation);

        final RestaurantEntity restaurantEntity = restaurantMapper.toEntity(restaurant);

        final RestaurantEntity createdEntity = restaurantRepository.save(restaurantEntity);

        return restaurantMapper.map(createdEntity);
    }

    @Override
    public Restaurant update(@NotNull final Restaurant restaurant) {

        final Location replaceLocation = locationUseCase.getOneByZipCodeOrCreated(restaurant.getLocation());

        restaurant.setLocation(replaceLocation);

        final RestaurantEntity restaurantEntity = restaurantMapper.toEntity(restaurant);

        final RestaurantEntity savedEntity = restaurantRepository.save(restaurantEntity);

        return restaurantMapper.map(savedEntity);
    }

    @Override
    public Optional<Restaurant> findById(@NotNull final Long id) {

        final RestaurantEntity savedEntity = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Restaurant not found"));

        final Restaurant restaurant = restaurantMapper.map(savedEntity);

        return Optional.of(restaurant);
    }

    @Override
    public List<Restaurant> findAllActive(final int page, final int size) {

        final Pageable pageable = PageRequest.of(page, size);

        return restaurantRepository.findAllByActive(pageable)
                .stream()
                .map(restaurantMapper::map)
                .collect(Collectors.toList());
    }
}
