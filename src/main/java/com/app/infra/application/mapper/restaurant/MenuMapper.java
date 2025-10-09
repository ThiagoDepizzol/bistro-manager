package com.app.infra.application.mapper.restaurant;

import com.app.core.domain.restaurant.Menu;
import com.app.infra.application.dto.restaurant.MenuDTO;
import com.app.infra.application.request.restaurant.MenuRequest;
import com.app.infra.entity.restaurant.MenuEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MenuMapper {

    private final RestaurantMapper restaurantMapper;

    public MenuMapper(final RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }

    public Menu map(@NotNull final MenuRequest request) {

        final Menu menu = new Menu();

        menu.setId(request.getId());
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());
        menu.setPrice(request.getPrice());
        menu.setImage(request.getImage());

        if (Objects.nonNull(request.getRestaurant())) {

            menu.setRestaurant(restaurantMapper.map(request.getRestaurant()));

        }

        return menu;
    }

    public Menu map(@NotNull final MenuRequest request, @NotNull final Long id) {

        final Menu menu = new Menu();

        menu.setId(id);
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());
        menu.setPrice(request.getPrice());
        menu.setImage(request.getImage());

        if (Objects.nonNull(request.getRestaurant())) {

            menu.setRestaurant(restaurantMapper.map(request.getRestaurant()));

        }

        return menu;
    }

    public Menu map(@NotNull final MenuEntity entity) {

        final Menu menu = new Menu();

        menu.setId(entity.getId());
        menu.setName(entity.getName());
        menu.setDescription(entity.getDescription());
        menu.setPrice(entity.getPrice());
        menu.setImage(entity.getImage());

        if (Objects.nonNull(entity.getRestaurant())) {

            menu.setRestaurant(restaurantMapper.map(entity.getRestaurant()));

        }

        return menu;
    }

    public MenuEntity toEntity(@NotNull final Menu menu) {

        final MenuEntity entity = new MenuEntity();

        entity.setId(menu.getId());
        entity.setName(menu.getName());
        entity.setDescription(menu.getDescription());
        entity.setPrice(menu.getPrice());
        entity.setImage(menu.getImage());

        if (Objects.nonNull(menu.getRestaurant())) {

            entity.setRestaurant(restaurantMapper.toEntity(menu.getRestaurant()));

        }

        return entity;
    }

    public MenuDTO toDTO(@NotNull final Menu menu) {

        final MenuDTO dto = new MenuDTO();

        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setDescription(menu.getDescription());
        dto.setPrice(menu.getPrice());
        dto.setImage(menu.getImage());

        if (Objects.nonNull(menu.getRestaurant())) {

            dto.setRestaurant(restaurantMapper.toDTO(menu.getRestaurant()));

        }

        return dto;
    }


}
