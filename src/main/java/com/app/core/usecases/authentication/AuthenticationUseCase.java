package com.app.core.usecases.authentication;

import com.app.core.gateways.authentication.AuthenticationGateway;
import com.app.infra.controller.authentication.dto.LoginDTO;

public class AuthenticationUseCase {

    private final AuthenticationGateway authenticationGateway;

    public AuthenticationUseCase(AuthenticationGateway authenticationGateway) {
        this.authenticationGateway = authenticationGateway;
    }

    public void authentication(final LoginDTO dto) {

        authenticationGateway.authentication(dto);

    }

}
