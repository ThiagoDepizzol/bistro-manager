package com.app.infra.main.roles;

import com.app.core.gateways.roles.RoleGateway;
import com.app.core.usecases.roles.RoleUseCase;
import com.app.infra.application.mapper.roles.RoleMapper;
import com.app.infra.gateway.roles.RoleRepositoryGateway;
import com.app.infra.repository.roles.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RoleConfiguration {

    @Bean
    RoleUseCase createdRoleUseCase(final RoleGateway roleGateway) {
        return new RoleUseCase(roleGateway);
    }

    @Bean
    RoleGateway createdRoleGateway(final RoleMapper roleMapper, final RoleRepository roleRepository) {
        return new RoleRepositoryGateway(roleMapper, roleRepository);
    }

}
