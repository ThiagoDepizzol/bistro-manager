package com.app.infra.application.controller.restaurant;

import com.app.core.domain.restaurant.Menu;
import com.app.core.usecases.restaurant.MenuUseCase;
import com.app.core.usecases.validation.menu.MenuAuthorizationUseCase;
import com.app.infra.application.dto.restaurant.MenuDTO;
import com.app.infra.application.mapper.restaurant.MenuMapper;
import com.app.infra.application.request.restaurant.MenuRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("res/menus")
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    private final MenuMapper menuMapper;

    private final MenuUseCase menuUseCase;

    private final MenuAuthorizationUseCase menuAuthorizationUseCase;

    public MenuController(final MenuMapper menuMapper, final MenuUseCase menuUseCase, final MenuAuthorizationUseCase menuAuthorizationUseCase) {
        this.menuMapper = menuMapper;
        this.menuUseCase = menuUseCase;
        this.menuAuthorizationUseCase = menuAuthorizationUseCase;
    }

    @PostMapping
    public ResponseEntity<MenuDTO> created(@RequestBody final MenuRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {

        log.info("POST -> /res/menus -> {}, {}", request, header);

        final Menu menu = menuMapper.map(request);

        menuAuthorizationUseCase.hasPermissionCreatedOrUpdate(header, menu);

        final Menu menuCreated = menuUseCase.created(menu);

        final MenuDTO menuDTO = menuMapper.toDTO(menuCreated);

        return ResponseEntity.ok(menuDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuDTO> update(@PathVariable final Long id, @RequestBody final MenuRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {

        log.info("PUT -> /res/menus -> {}, {}", id, request);

        final Menu menu = menuMapper.map(request, id);

        menuAuthorizationUseCase.hasPermissionCreatedOrUpdate(header, menu);

        final Menu menuUpdate = menuUseCase.update(menu);

        final MenuDTO dto = menuMapper.toDTO(menuUpdate);

        return ResponseEntity.ok(dto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> get(@PathVariable final Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {

        log.info("GET -> /res/menus -> {}", id);

        final MenuDTO dto = menuUseCase.findById(id)
                .map(menuMapper::toDTO)
                .orElseThrow(() -> new IllegalStateException("Menu not found"));

        return ResponseEntity.ok(dto);

    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAll(@RequestParam final int page, @RequestParam final int size, @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {

        log.info("GET -> /res/menus -> {}, {}", page, size);

        final List<Menu> menus = menuUseCase.findAllActive(page, size);

        final List<MenuDTO> dtos = menus
                .stream()
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);

    }

}
