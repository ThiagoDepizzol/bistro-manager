package com.app.infra.controller.authentication.mapper;

import com.app.infra.controller.authentication.dto.LoginDTO;
import com.app.infra.controller.authentication.json.LoginJson;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapper {

    public LoginDTO mapToLoginDTO(@NotNull final LoginJson json) {
        return new LoginDTO(json.getLogin(), json.getPassword());
    }

}
