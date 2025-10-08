package com.app.infra.application.mapper.roles;

import com.app.core.domain.role.Role;
import com.app.infra.application.dto.roles.RoleDTO;
import com.app.infra.application.request.roles.RoleRequest;
import com.app.infra.entity.roles.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role mapToRole(RoleRequest request) {
        return new Role(
                request.getId(),
                request.getName(),
                request.getType(),
                request.getDescription()
        );
    }

    public Role mapToRole(RoleEntity entity) {
        return new Role(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getDescription()
        );
    }

    public Role mapToRole(RoleRequest request, Long id) {
        return new Role(
                id,
                request.getName(),
                request.getType(),
                request.getDescription()
        );
    }

    public RoleEntity toEntity(Role role) {
        return new RoleEntity(
                role.getId(),
                role.getName(),
                role.getType(),
                role.getDescription()
        );
    }

    public RoleDTO mapToDTO(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getName(),
                role.getType(),
                role.getDescription()
        );
    }

}
