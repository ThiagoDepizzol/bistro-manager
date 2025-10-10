package com.app.core.usecases.validation.menu;

import com.app.core.domain.enums.RoleType;
import com.app.core.domain.restaurant.Menu;
import com.app.core.gateways.validation.menu.MenuAuthorizationGateway;
import com.app.core.usecases.authentication.AuthenticationUseCase;
import com.app.infra.application.dto.authentication.LoginDTO;
import com.app.infra.application.mapper.authentication.AuthenticationMapper;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
import java.util.List;

public class MenuAuthorizationUseCase {

    private final AuthenticationUseCase authenticationUseCase;

    private final AuthenticationMapper authenticationMapper;

    private final MenuAuthorizationGateway menuAuthorizationGateway;

    public MenuAuthorizationUseCase(final AuthenticationUseCase authenticationUseCase, final AuthenticationMapper authenticationMapper, final MenuAuthorizationGateway menuAuthorizationGateway) {
        this.authenticationUseCase = authenticationUseCase;
        this.authenticationMapper = authenticationMapper;
        this.menuAuthorizationGateway = menuAuthorizationGateway;
    }

    public void hasPermissionCreatedOrUpdate(@NotNull final String header, @NotNull final Menu menu) {

        final LoginDTO dto = authenticationMapper.map(header);

        final List<RoleType> types = Arrays.asList(RoleType.SYSTEM_ADMIN, RoleType.RESTAURANT_OWNER);

        authenticationUseCase.isUserAuthenticated(dto);

        menuAuthorizationGateway.userHasPermissionToCreated(dto, types, menu);


    }

}
