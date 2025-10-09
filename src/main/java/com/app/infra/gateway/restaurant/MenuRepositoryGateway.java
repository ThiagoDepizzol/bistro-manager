package com.app.infra.gateway.restaurant;

import com.app.core.domain.restaurant.Menu;
import com.app.core.gateways.restaurant.MenuGateway;
import com.app.infra.application.mapper.restaurant.MenuMapper;
import com.app.infra.entity.restaurant.MenuEntity;
import com.app.infra.repository.restaurant.MenuRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MenuRepositoryGateway implements MenuGateway {

    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    public MenuRepositoryGateway(final MenuRepository menuRepository, final MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    public Menu created(@NotNull final Menu menu) {

        final MenuEntity menuEntity = menuMapper.toEntity(menu);

        final MenuEntity createdEntity = menuRepository.save(menuEntity);

        return menuMapper.map(createdEntity);
    }

    @Override
    public Menu update(@NotNull final Menu menu) {

        final MenuEntity menuEntity = menuMapper.toEntity(menu);

        final MenuEntity updateEntity = menuRepository.save(menuEntity);

        return menuMapper.map(updateEntity);
    }

    @Override
    public Optional<Menu> findById(@NotNull final Long id) {

        final MenuEntity savedEntity = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Menu not found"));

        final Menu menu = menuMapper.map(savedEntity);

        return Optional.of(menu);
    }

    @Override
    public List<Menu> findAllActive(final int page, final int size) {

        final Pageable pageable = PageRequest.of(page, size);

        return menuRepository.findAllByActive(pageable)
                .stream()
                .map(menuMapper::map)
                .collect(Collectors.toList());
    }
}
