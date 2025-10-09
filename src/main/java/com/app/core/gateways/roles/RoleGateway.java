package com.app.core.gateways.roles;

import com.app.core.domain.role.Role;

import java.util.List;
import java.util.Optional;

public interface RoleGateway {

    Role created(Role role);

    Role update(Role role);

    Optional<Role> findById(Long id);

    Optional<Role> findOneSystemAdmin();

    List<Role> findAllActive(int page, int size);
}
