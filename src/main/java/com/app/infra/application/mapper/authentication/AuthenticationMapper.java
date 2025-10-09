package com.app.infra.application.mapper.authentication;

import com.app.infra.application.dto.authentication.LoginDTO;
import com.app.infra.application.request.authentication.LoginRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapper {

    public LoginDTO map(@NotNull final LoginRequest request) {

        final LoginDTO dto = new LoginDTO();

        dto.setLogin(request.getLogin());
        dto.setPassword(request.getPassword());

        return dto;

    }
}
