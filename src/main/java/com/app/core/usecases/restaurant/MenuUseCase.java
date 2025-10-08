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

    public Menu save(final Menu menu) {

        return menuGateway.save(menu);
    }

    public Optional<Menu> findById(final Long id) {

        return menuGateway.findById(id);
    }

    public List<Menu> findAllActive(final int page, final int size) {

        return menuGateway.findAllActive(page, size);
    }
}
