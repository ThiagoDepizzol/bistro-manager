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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

        final MenuDTO menuDTO = menuMapper.toDTO(menuCreated);

        return ResponseEntity.ok(menuDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuDTO> update(@PathVariable final Long id, @RequestBody final MenuRequest request) {

        log.info("PUT -> /res/menus -> {}, {}", id, request);

        final Restaurant restaurant = restaurantMapper.mapToRestaurant(request.getRestaurant());

        final Menu menu = menuMapper.mapToManu(request, restaurant, id);

        final Menu menuUpdate = menuUseCase.save(menu);

        final MenuDTO dto = menuMapper.toDTO(menuUpdate);

        return ResponseEntity.ok(dto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> get(@PathVariable final Long id) {

        log.info("GET -> /res/menus -> {}", id);

        final MenuDTO dto = menuUseCase.findById(id)
                .map(menuMapper::toDTO)
                .orElseThrow(() -> new IllegalStateException("Menu not found"));

        return ResponseEntity.ok(dto);

    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAll(@RequestParam final int page, @RequestParam final int size) {

        log.info("GET -> /usr/users -> {}, {}", page, size);

        final List<Menu> menus = menuUseCase.findAllActive(page, size);

        final List<MenuDTO> dtos = menus
                .stream()
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);

    }

}
