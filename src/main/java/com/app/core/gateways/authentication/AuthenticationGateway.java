package com.app.core.gateways.authentication;

import com.app.infra.application.dto.authentication.LoginDTO;

public interface AuthenticationGateway {

    void authentication(LoginDTO dto);

    void disconnect(String login, String password);

    void isUserAuthenticated(final LoginDTO dto);

}
