package com.app.infra.gateway.restaurant;

import com.app.core.domain.location.Location;
import com.app.core.domain.restaurant.Restaurant;
import com.app.core.gateways.restaurant.RestaurantGateway;
import com.app.core.usecases.location.LocationUseCase;
import com.app.infra.application.mapper.location.LocationMapper;
import com.app.infra.application.mapper.restaurant.RestaurantMapper;
import com.app.infra.application.mapper.user.UserMapper;
import com.app.infra.entity.location.LocationEntity;
import com.app.infra.entity.restaurant.RestaurantEntity;
import com.app.infra.entity.user.UserEntity;
import com.app.infra.repository.location.LocationRepository;
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

    private final LocationMapper locationMapper;

    private final UserMapper userMapper;

    private final RestaurantRepository restaurantRepository;

    private final LocationUseCase locationUseCase;

    private final LocationRepository locationRepository;

    public RestaurantRepositoryGateway(final RestaurantMapper restaurantMapper, final LocationMapper locationMapper, final UserMapper userMapper, final RestaurantRepository restaurantRepository, final LocationUseCase locationUseCase, final LocationRepository locationRepository) {
        this.restaurantMapper = restaurantMapper;
        this.locationMapper = locationMapper;
        this.userMapper = userMapper;
        this.restaurantRepository = restaurantRepository;
        this.locationUseCase = locationUseCase;
        this.locationRepository = locationRepository;
    }

    @Override
    public Restaurant save(@NotNull final Restaurant restaurant) {


        final Location location = Optional.ofNullable(restaurant.getLocation())
                .flatMap(loc -> {

                    final String zipcode = Optional.ofNullable(loc.getZipCode())
                            .map(code -> code.replaceAll("[^a-zA-Z0-9]", ""))
                            .orElse(null);

                    return locationRepository.findOneByZipCode(zipcode)
                            .map(locationMapper::mapToLocation);
                })
                .orElseGet(() -> locationUseCase.save(restaurant.getLocation()));

        final LocationEntity locationEntity = locationMapper.toEntity(location);

        final UserEntity userEntity = userMapper.toNewEntityWithoutRole(restaurant.getRestaurantOwner());

        final RestaurantEntity restaurantEntity = restaurantMapper.toEntity(restaurant, locationEntity, userEntity);

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
