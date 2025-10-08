package com.app.infra.application.mapper.restaurant;

import com.app.core.domain.restaurant.Menu;
import com.app.core.domain.restaurant.Restaurant;
import com.app.infra.application.dto.restaurant.MenuDTO;
import com.app.infra.application.dto.restaurant.RestaurantDTO;
import com.app.infra.application.request.restaurant.MenuRequest;
import com.app.infra.entity.restaurant.MenuEntity;
import com.app.infra.entity.restaurant.RestaurantEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    private final RestaurantMapper restaurantMapper;

    public MenuMapper(final RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }

    public Menu mapToManu(@NotNull final MenuRequest request, @NotNull final Restaurant restaurant) {

        return new Menu(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                restaurant,
                request.getImage()
        );
    }

    public Menu mapToManu(@NotNull final MenuRequest request, @NotNull final Restaurant restaurant, @NotNull final Long id) {

        return new Menu(
                id,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                restaurant,
                request.getImage()
        );
    }

    public Menu mapToManu(@NotNull final MenuEntity entity, @NotNull final Restaurant restaurant) {

        return new Menu(
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                restaurant,
                entity.getImage()
        );
    }

    public MenuEntity toEntity(@NotNull final Menu menu, @NotNull final RestaurantEntity restaurant) {
        return new MenuEntity(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getPrice(),
                restaurant,
                menu.getImage()
        );
    }

    public MenuDTO toDTO(@NotNull final Menu menu) {

        final RestaurantDTO restaurant = restaurantMapper.mapToDTO(menu.getRestaurant());

        return new MenuDTO(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getPrice(),
                restaurant,
                menu.getImage()
        );
    }

}
