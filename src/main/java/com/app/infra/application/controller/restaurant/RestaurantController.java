package com.app.infra.application.controller.restaurant;

import com.app.core.domain.location.Location;
import com.app.core.domain.restaurant.Restaurant;
import com.app.core.usecases.restaurant.RestaurantUseCase;
import com.app.infra.application.dto.restaurant.RestaurantDTO;
import com.app.infra.application.mapper.location.LocationMapper;
import com.app.infra.application.mapper.restaurant.RestaurantMapper;
import com.app.infra.application.request.restaurant.RestaurantRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("res/restaurants")
public class RestaurantController {

    private static final Logger log = LoggerFactory.getLogger(RestaurantController.class);

    private final LocationMapper locationMapper;

    private final RestaurantMapper restaurantMapper;

    private final RestaurantUseCase restaurantUseCase;

    public RestaurantController(final LocationMapper locationMapper, final RestaurantMapper restaurantMapper, final RestaurantUseCase restaurantUseCase) {
        this.locationMapper = locationMapper;
        this.restaurantMapper = restaurantMapper;
        this.restaurantUseCase = restaurantUseCase;
    }

    @PostMapping
    public ResponseEntity<RestaurantDTO> created(@RequestBody final RestaurantRequest request) {

        log.info("POST -> /res/restaurants -> {}", request);

        final Location location = locationMapper.mapToLocation(request.getLocation());

        final Restaurant restaurant = restaurantMapper.mapToRestaurant(request, location);

        final Restaurant restaurantCreated = restaurantUseCase.save(restaurant);

        final RestaurantDTO dto = restaurantMapper.mapToDTO(restaurantCreated);

        return ResponseEntity.ok(dto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDTO> update(@PathVariable final Long id, @RequestBody final RestaurantRequest request) {

        log.info("PUT -> /res/restaurants -> {}, {}", id, request);

        final Restaurant restaurant = restaurantMapper.mapToRestaurant(request, id);

        final Restaurant restaurantCreated = restaurantUseCase.save(restaurant);

        final RestaurantDTO dto = restaurantMapper.mapToDTO(restaurantCreated);

        return ResponseEntity.ok(dto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> get(@PathVariable final Long id) {

        log.info("GET -> /res/restaurants -> {}", id);

        final RestaurantDTO dto = restaurantUseCase.findById(id)
                .map(restaurantMapper::mapToDTO)
                .orElseThrow(() -> new IllegalStateException("Restaurant not found"));

        return ResponseEntity.ok(dto);

    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAll(@RequestParam final int page, @RequestParam final int size) {

        log.info("GET -> /res/restaurants -> {}, {}", page, size);

        final List<Restaurant> restaurants = restaurantUseCase.findAllActive(page, size);

        final List<RestaurantDTO> dtos = restaurants
                .stream()
                .map(restaurantMapper::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);

    }

}
