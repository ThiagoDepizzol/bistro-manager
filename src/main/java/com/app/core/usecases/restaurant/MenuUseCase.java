package com.app.core.usecases.restaurant;

import com.app.core.domain.restaurant.Menu;
import com.app.core.gateways.restaurant.MenuGateway;

import java.util.List;
import java.util.Optional;

public class MenuUseCase {

    private final MenuGateway menuGateway;

    public MenuUseCase(MenuGateway menuGateway) {
        this.menuGateway = menuGateway;
    }

    public Menu created(final Menu menu) {

        return menuGateway.created(menu);
    }

    public Menu update(final Menu menu) {

        return menuGateway.update(menu);
    }

    public Optional<Menu> findById(final Long id) {

        return menuGateway.findById(id);
    }

    public List<Menu> findAllActive(final int page, final int size, String header) {

        return menuGateway.findAllActive(page, size, header);
    }

    public List<Menu> getAllByRestaurant(final Long restaurantId) {

        return menuGateway.getAllByRestaurant(restaurantId);
    }
}
