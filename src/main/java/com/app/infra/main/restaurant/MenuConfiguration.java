package com.app.infra.main.restaurant;

import com.app.core.gateways.restaurant.MenuGateway;
import com.app.core.usecases.restaurant.MenuUseCase;
import com.app.infra.application.mapper.authentication.AuthenticationMapper;
import com.app.infra.application.mapper.restaurant.MenuMapper;
import com.app.infra.gateway.restaurant.MenuRepositoryGateway;
import com.app.infra.repository.restaurant.MenuRepository;
import com.app.infra.repository.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MenuConfiguration {

    @Bean
    MenuUseCase createdMenuUseCase(final MenuGateway menuGateway) {
        return new MenuUseCase(menuGateway);
    }

    @Bean
    MenuGateway createdMenuGateway(final MenuRepository menuRepository, final MenuMapper menuMapper, final AuthenticationMapper authenticationMapper, final UserRepository userRepository) {
        return new MenuRepositoryGateway(menuRepository, menuMapper, authenticationMapper, userRepository);
    }

}
