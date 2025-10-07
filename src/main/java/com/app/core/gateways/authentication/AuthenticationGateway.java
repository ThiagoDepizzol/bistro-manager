package com.app.core.gateways.authentication;

import com.app.infra.controller.authentication.dto.LoginDTO;

public interface AuthenticationGateway {

    void authentication(LoginDTO dto);

    void disconnect(String login, String password);

}
