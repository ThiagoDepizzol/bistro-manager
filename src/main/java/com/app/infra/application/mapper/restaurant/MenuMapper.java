package com.app.infra.application.mapper.restaurant;

import com.app.core.domain.restaurant.Menu;
import com.app.core.domain.restaurant.Restaurant;
import com.app.infra.application.request.restaurant.MenuRequest;
import com.app.infra.entity.restaurant.MenuEntity;
import com.app.infra.entity.restaurant.RestaurantEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    public Menu mapToManu(@NotNull final MenuRequest request, @NotNull final Restaurant restaurant) {

        return new Menu(
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

}
