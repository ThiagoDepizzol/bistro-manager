package com.app.infra.gateway.roles;

import com.app.core.domain.role.Role;
import com.app.core.gateways.roles.RoleGateway;
import com.app.infra.application.mapper.roles.RoleMapper;
import com.app.infra.entity.roles.RoleEntity;
import com.app.infra.repository.roles.RoleRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoleRepositoryGateway implements RoleGateway {

    private final RoleMapper roleMapper;

    private final RoleRepository roleRepository;

    public RoleRepositoryGateway(final RoleMapper roleMapper, final RoleRepository roleRepository) {
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public Role created(@NotNull final Role role) {

        final RoleEntity roleEntity = roleMapper.toEntity(role);

        final RoleEntity createdEntity = roleRepository.save(roleEntity);

        return roleMapper.map(createdEntity);
    }


    @Override
    public Role update(@NotNull final Role role) {

        final RoleEntity roleEntity = roleMapper.toEntity(role);

        final RoleEntity updateEntity = roleRepository.save(roleEntity);

        return roleMapper.map(updateEntity);
    }

    @Override
    public Optional<Role> findById(@NotNull final Long id) {

        final RoleEntity savedEntity = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        final Role role = roleMapper.map(savedEntity);

        return Optional.ofNullable(role);
    }

    @Override
    public List<Role> findAllActive(final int page, final int size) {

        final Pageable pageable = PageRequest.of(page, size);

        return roleRepository.findAllByActive(pageable)
                .stream()
                .map(roleMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Role> findOneSystemAdmin() {

        return roleRepository.findOneByActive()
                .map(roleMapper::map);
    }
}
