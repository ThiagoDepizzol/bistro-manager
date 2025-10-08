package com.app.core.gateways.roles;

import com.app.core.domain.role.Role;

import java.util.List;
import java.util.Optional;

public interface RoleGateway {

    Role save(Role role);

    Optional<Role> findById(Long id);

    List<Role> findAllActive(int page, int size);
}
