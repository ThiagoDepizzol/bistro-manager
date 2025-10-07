package com.app.infra.application.controller.authentication;

import com.app.core.usecases.authentication.AuthenticationUseCase;
import com.app.infra.application.dto.authentication.LoginDTO;
import com.app.infra.application.mapper.authentication.AuthenticationMapper;
import com.app.infra.application.request.authentication.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationMapper authenticationMapper;

    private final AuthenticationUseCase authenticationUseCase;

    public AuthenticationController(final AuthenticationMapper authenticationMapper, final AuthenticationUseCase authenticationUseCase) {
        this.authenticationMapper = authenticationMapper;
        this.authenticationUseCase = authenticationUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final LoginRequest json) {

        log.info("POST -> /login -> {} ", json);

        final LoginDTO dto = authenticationMapper.mapToLoginDTO(json);

        authenticationUseCase.authentication(dto);

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping("/disconnect")
    public ResponseEntity<?> disconnect(HttpServletRequest request) {

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {

            final String base64Credentials = authorizationHeader.substring("Basic ".length());

            final byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            final String decodedCredentials = new String(decodedBytes);

            String[] credentials = decodedCredentials.split(":", 2);

            if (credentials.length == 2) {

                final String login = credentials[0];
                final String password = credentials[1];

                authenticationUseCase.disconnect(login, password);
            }
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
