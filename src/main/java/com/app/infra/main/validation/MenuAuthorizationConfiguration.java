package com.app.infra.main.validation;

import com.app.core.gateways.validation.menu.MenuAuthorizationGateway;
import com.app.core.usecases.authentication.AuthenticationUseCase;
import com.app.core.usecases.validation.menu.MenuAuthorizationUseCase;
import com.app.infra.application.mapper.authentication.AuthenticationMapper;
import com.app.infra.gateway.validation.menu.MenuAuthorizationRepositoryGateway;
import com.app.infra.repository.restaurant.RestaurantRepository;
import com.app.infra.repository.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MenuAuthorizationConfiguration {

    @Bean
    MenuAuthorizationUseCase createdMenuAuthorizationUseCase(final AuthenticationUseCase authenticationUseCase, final AuthenticationMapper authenticationMapper, final MenuAuthorizationGateway menuAuthorizationGateway) {
        return new MenuAuthorizationUseCase(authenticationUseCase, authenticationMapper, menuAuthorizationGateway);
    }

    @Bean
    MenuAuthorizationGateway createdMenuAuthorizationGateway(final UserRepository userRepository, final RestaurantRepository restaurantRepository) {
        return new MenuAuthorizationRepositoryGateway(userRepository, restaurantRepository);
    }
}
