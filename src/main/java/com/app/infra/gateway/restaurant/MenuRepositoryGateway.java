package com.app.infra.gateway.restaurant;

import com.app.core.domain.restaurant.Menu;
import com.app.core.domain.restaurant.Restaurant;
import com.app.core.gateways.restaurant.MenuGateway;
import com.app.infra.application.mapper.restaurant.MenuMapper;
import com.app.infra.application.mapper.restaurant.RestaurantMapper;
import com.app.infra.entity.restaurant.MenuEntity;
import com.app.infra.entity.restaurant.RestaurantEntity;
import com.app.infra.repository.restaurant.MenuRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MenuRepositoryGateway implements MenuGateway {

    private final RestaurantMapper restaurantMapper;

    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    public MenuRepositoryGateway(final RestaurantMapper restaurantMapper, final MenuRepository menuRepository, final MenuMapper menuMapper) {
        this.restaurantMapper = restaurantMapper;
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    public Menu save(@NotNull final Menu menu) {

        final RestaurantEntity restaurantEntity = restaurantMapper.toEntity(menu.getRestaurant());

        final MenuEntity menuEntity = menuMapper.toEntity(menu, restaurantEntity);

        final MenuEntity savedEntity = menuRepository.save(menuEntity);

        final Restaurant restaurant = restaurantMapper.mapToRestaurant(savedEntity.getRestaurant());

        return menuMapper.mapToManu(savedEntity, restaurant);
    }

    @Override
    public Optional<Menu> findById(@NotNull final Long id) {

        final MenuEntity savedEntity = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Menu not found"));

        final Restaurant restaurant = restaurantMapper.mapToRestaurant(savedEntity.getRestaurant());

        final Menu menu = menuMapper.mapToManu(savedEntity, restaurant);

        return Optional.of(menu);
    }

    @Override
    public List<Menu> findAllActive(final int page, final int size) {

        final Pageable pageable = PageRequest.of(page, size);

        final Page<MenuEntity> entities = menuRepository.findAllByActive(pageable);

        return entities
                .stream()
                .map(entity -> {

                    final Restaurant restaurant = restaurantMapper.mapToRestaurant(entity.getRestaurant());

                    return menuMapper.mapToManu(entity, restaurant);

                })
                .collect(Collectors.toList());
    }
}
