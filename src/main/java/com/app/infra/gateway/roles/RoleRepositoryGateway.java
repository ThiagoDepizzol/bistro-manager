package com.app.infra.gateway.roles;

import com.app.core.domain.role.Role;
import com.app.core.gateways.roles.RoleGateway;
import com.app.infra.application.mapper.roles.RoleMapper;
import com.app.infra.entity.roles.RoleEntity;
import com.app.infra.repository.roles.RoleRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
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
    public Role save(@NotNull final Role role) {

        final RoleEntity roleEntity = roleMapper.toEntity(role);

        final RoleEntity savedEntity = roleRepository.save(roleEntity);

        return roleMapper.mapToRole(savedEntity);
    }

    @Override
    public Optional<Role> findById(@NotNull final Long id) {

        final RoleEntity savedEntity = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        final Role role = roleMapper.mapToRole(savedEntity);

        return Optional.of(role);
    }

    @Override
    public List<Role> findAllActive(final int page, final int size) {

        final Pageable pageable = PageRequest.of(page, size);

        final Page<RoleEntity> entities = roleRepository.findAllByActive(pageable);

        return entities
                .stream()
                .map(roleMapper::mapToRole)
                .collect(Collectors.toList());
    }
}
