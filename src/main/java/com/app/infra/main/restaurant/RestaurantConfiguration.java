package com.app.infra.main.restaurant;

import com.app.core.gateways.restaurant.RestaurantGateway;
import com.app.core.usecases.restaurant.RestaurantUseCase;
import com.app.infra.application.mapper.restaurant.RestaurantMapper;
import com.app.infra.gateway.restaurant.RestaurantRepositoryGateway;
import com.app.infra.repository.restaurant.RestaurantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RestaurantConfiguration {

    @Bean
    RestaurantUseCase createdRestaurantUseCase(final RestaurantGateway restaurantGateway) {
        return new RestaurantUseCase(restaurantGateway);
    }

    @Bean
    RestaurantGateway createdRestaurantGateway(final RestaurantMapper restaurantMapper, final RestaurantRepository restaurantRepository) {
        return new RestaurantRepositoryGateway(restaurantMapper, restaurantRepository);
    }

}
