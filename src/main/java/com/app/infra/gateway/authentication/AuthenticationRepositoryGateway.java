package com.app.infra.gateway.authentication;

import com.app.core.gateways.authentication.AuthenticationGateway;
import com.app.core.utils.PasswordUtils;
import com.app.infra.application.dto.authentication.LoginDTO;
import com.app.infra.entity.user.UserEntity;
import com.app.infra.repository.user.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;

public class AuthenticationRepositoryGateway implements AuthenticationGateway {


    private static final Logger log = LoggerFactory.getLogger(AuthenticationRepositoryGateway.class);

    private final UserRepository userRepository;

    public AuthenticationRepositoryGateway(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void authentication(@NotNull final LoginDTO dto) {

        final UserEntity userEntity = userRepository.findByEmail(dto.getLogin())
                .orElseThrow(() -> {

                    log.info("Email incorreto");

                    //TODO: Refatorar regra de exception
                    return new IllegalStateException("Usuário ou senha inválidos");
                });


        final boolean isPasswordCorrect = PasswordUtils.matches(dto.getPassword(), userEntity.getPassword());

        if (!isPasswordCorrect) {


            log.info("Senha incorreto");

            //TODO: Refatorar regra de exception
            throw new IllegalStateException("Usuário ou senha inválidos");

        }

        final UUID uuid = UUID.randomUUID();

        userEntity.setLoginHash(uuid);
        userEntity.setLoginDate(Instant.now());

        userRepository.save(userEntity);

    }

    @Override
    public void disconnect(@NotNull final String login, @NotNull final String password) {

        final UserEntity userEntity = userRepository.findByEmail(login)
                .orElseThrow(() -> {

                    log.info("Email incorreto");

                    //TODO: Refatorar regra de exception
                    return new IllegalStateException("Usuário ou senha inválidos");
                });

        final boolean isPasswordCorrect = PasswordUtils.matches(password, userEntity.getPassword());

        if (!isPasswordCorrect) {


            log.info("Senha incorreto");

            //TODO: Refatorar regra de exception
            throw new IllegalStateException("Usuário ou senha inválidos");

        }

        userEntity.setLoginHash(null);
        userEntity.setLoginDate(null);

        userRepository.save(userEntity);


    }

}
