package com.app.infra.main.location;

import com.app.core.gateways.location.LocationGateway;
import com.app.core.usecases.location.LocationUseCase;
import com.app.infra.application.mapper.location.LocationMapper;
import com.app.infra.gateway.location.LocationRepositoryGateway;
import com.app.infra.repository.location.LocationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class LocationConfiguration {

    @Bean
    LocationUseCase createdLocationUseCase(final LocationGateway locationGateway) {
        return new LocationUseCase(locationGateway);
    }

    @Bean
    LocationGateway createdLocationGateway(final LocationMapper locationMapper, final LocationRepository locationRepository) {
        return new LocationRepositoryGateway(locationMapper, locationRepository);
    }

}
