package com.app.infra.application.mapper.authentication;

import com.app.infra.application.dto.authentication.LoginDTO;
import com.app.infra.application.request.authentication.LoginRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class AuthenticationMapper {

    public LoginDTO map(@NotNull final LoginRequest request) {

        final LoginDTO dto = new LoginDTO();

        dto.setLogin(request.getLogin());
        dto.setPassword(request.getPassword());

        return dto;

    }

    public LoginDTO map(@NotNull final String header) {

        final LoginDTO dto = new LoginDTO();


        String base64Credentials = header.substring("Basic ".length());

        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);

        String decodedString = new String(decodedBytes);
        String[] credentials = decodedString.split(":", 2);

        String username = credentials[0];
        String password = credentials.length > 1 ? credentials[1] : "";

        dto.setLogin(username);
        dto.setPassword(password);

        return dto;

    }
}
