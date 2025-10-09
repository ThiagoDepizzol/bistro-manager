package com.app.infra.application.mapper.roles;

import com.app.core.domain.role.Role;
import com.app.infra.application.dto.roles.RoleDTO;
import com.app.infra.application.request.roles.RoleRequest;
import com.app.infra.entity.roles.RoleEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role map(@NotNull final RoleRequest request) {

        final Role role = new Role();

        role.setId(request.getId());
        role.setName(request.getName());
        role.setType(request.getType());
        role.setDescription(request.getDescription());

        return role;
    }

    public Role map(@NotNull final RoleRequest request, @NotNull final Long id) {

        final Role role = new Role();

        role.setId(id);
        role.setName(request.getName());
        role.setType(request.getType());
        role.setDescription(request.getDescription());

        return role;
    }

    public Role map(@NotNull final RoleEntity entity) {

        final Role role = new Role();

        role.setId(entity.getId());
        role.setName(entity.getName());
        role.setType(entity.getType());
        role.setDescription(entity.getDescription());

        return role;
    }

    public RoleEntity toEntity(@NotNull final Role role) {

        final RoleEntity entity = new RoleEntity();

        entity.setId(role.getId());
        entity.setName(role.getName());
        entity.setType(role.getType());
        entity.setDescription(role.getDescription());

        return entity;
    }

    public RoleDTO toDTO(@NotNull final Role role) {

        final RoleDTO dto = new RoleDTO();

        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setType(role.getType());
        dto.setDescription(role.getDescription());

        return dto;
    }

}
