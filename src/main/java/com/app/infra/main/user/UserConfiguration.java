package com.app.infra.main.user;

import com.app.core.gateways.user.UserGateway;
import com.app.core.usecases.user.UserUseCase;
import com.app.infra.application.mapper.user.UserMapper;
import com.app.infra.application.validator.user.UserValidator;
import com.app.infra.gateway.user.UserRepositoryGateway;
import com.app.infra.repository.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserConfiguration {

    @Bean
    UserUseCase createdUserUseCase(final UserGateway userGateway) {
        return new UserUseCase(userGateway);
    }

    @Bean
    UserGateway createdUserGateway(final UserMapper userMapper, final UserRepository userRepository, final UserValidator userValidator) {
        return new UserRepositoryGateway(userMapper, userRepository, userValidator);
    }

}
