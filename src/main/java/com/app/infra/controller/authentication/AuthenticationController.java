package com.app.infra.controller.authentication;

import com.app.core.usecases.authentication.AuthenticationUseCase;
import com.app.infra.controller.authentication.dto.LoginDTO;
import com.app.infra.controller.authentication.json.LoginJson;
import com.app.infra.controller.authentication.mapper.AuthenticationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> login(@RequestBody final LoginJson json) {

        log.info("POST -> /login -> {} ", json);

        final LoginDTO dto = authenticationMapper.mapToLoginDTO(json);

        authenticationUseCase.authentication(dto);

        return ResponseEntity.ok(HttpStatus.OK);

    }
}
