package com.app.core.usecases.roles;


import com.app.core.domain.role.Role;
import com.app.core.gateways.roles.RoleGateway;

import java.util.List;
import java.util.Optional;

public class RoleUseCase {

    private final RoleGateway roleGateway;

    public RoleUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public Role save(final Role role) {

        return roleGateway.save(role);
    }

    public Optional<Role> findById(final Long id) {

        return roleGateway.findById(id);
    }

    public Optional<Role> findOneSystemAdmin() {

        return roleGateway.findOneSystemAdmin();
    }

    public List<Role> findAllActive(final int page, final int size) {

        return roleGateway.findAllActive(page, size);
    }
}
