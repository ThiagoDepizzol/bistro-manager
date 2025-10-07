package com.app.infra.main.authentication;

import com.app.core.gateways.authentication.AuthenticationGateway;
import com.app.core.usecases.authentication.AuthenticationUseCase;
import com.app.infra.gateway.authentication.AuthenticationRepositoryGateway;
import com.app.infra.repository.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationConfiguration {

    @Bean
    AuthenticationUseCase createdAuthenticationUseCase(final AuthenticationGateway authenticationGateway) {
        return new AuthenticationUseCase(authenticationGateway);
    }

    @Bean
    AuthenticationRepositoryGateway createdAuthenticationRepositoryGateway(final UserRepository userRepository) {
        return new AuthenticationRepositoryGateway(userRepository);
    }

}
