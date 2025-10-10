package com.app.core.gateways.restaurant;

import com.app.core.domain.restaurant.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuGateway {

    Menu created(Menu menu);

    Menu update(Menu menu);

    Optional<Menu> findById(Long id);

    List<Menu> findAllActive(int page, int size, String header);

    List<Menu> getAllByRestaurant(final Long id);
}
