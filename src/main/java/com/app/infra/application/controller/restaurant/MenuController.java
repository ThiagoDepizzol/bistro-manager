package com.app.infra.application.controller.restaurant;

import com.app.core.domain.restaurant.Menu;
import com.app.core.domain.restaurant.Restaurant;
import com.app.core.usecases.restaurant.MenuUseCase;
import com.app.infra.application.dto.restaurant.MenuDTO;
import com.app.infra.application.mapper.restaurant.MenuMapper;
import com.app.infra.application.mapper.restaurant.RestaurantMapper;
import com.app.infra.application.request.restaurant.MenuRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("res/menus")
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    private final RestaurantMapper restaurantMapper;

    private final MenuMapper menuMapper;

    private final MenuUseCase menuUseCase;

    public MenuController(final RestaurantMapper restaurantMapper, final MenuMapper menuMapper, MenuUseCase menuUseCase) {
        this.restaurantMapper = restaurantMapper;
        this.menuMapper = menuMapper;
        this.menuUseCase = menuUseCase;
    }

    @PostMapping
    public ResponseEntity<MenuDTO> created(@RequestBody final MenuRequest request) {

        log.info("POST -> /res/menus -> {}", request);

        final Restaurant restaurant = restaurantMapper.mapToRestaurant(request.getRestaurant());

        final Menu menu = menuMapper.mapToManu(request, restaurant);

        final Menu menuCreated = menuUseCase.save(menu);

        return ResponseEntity.status(HttpStatus.OK).body(new MenuDTO());

    }

}
