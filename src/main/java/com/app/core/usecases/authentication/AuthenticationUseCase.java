package com.app.core.usecases.authentication;

import com.app.core.gateways.authentication.AuthenticationGateway;
import com.app.infra.application.dto.authentication.LoginDTO;

public class AuthenticationUseCase {

    private final AuthenticationGateway authenticationGateway;

    public AuthenticationUseCase(AuthenticationGateway authenticationGateway) {
        this.authenticationGateway = authenticationGateway;
    }

    public void authentication(final LoginDTO dto) {

        authenticationGateway.authentication(dto);

    }

    public void disconnect(final String login, final String password) {

        authenticationGateway.disconnect(login, password);

    }

    public void isUserAuthenticated(final LoginDTO dto) {

        authenticationGateway.isUserAuthenticated(dto);
    }

}
