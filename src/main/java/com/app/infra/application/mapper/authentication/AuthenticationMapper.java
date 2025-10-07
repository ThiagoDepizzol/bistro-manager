package com.app.infra.application.mapper.authentication;

import com.app.infra.application.dto.authentication.LoginDTO;
import com.app.infra.application.request.authentication.LoginRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapper {

    public LoginDTO mapToLoginDTO(@NotNull final LoginRequest json) {
        return new LoginDTO(json.getLogin(), json.getPassword());
    }

}
