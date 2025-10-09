package com.app.infra.gateway.location;

import com.app.core.domain.location.Location;
import com.app.core.gateways.location.LocationGateway;
import com.app.infra.application.mapper.location.LocationMapper;
import com.app.infra.entity.location.LocationEntity;
import com.app.infra.repository.location.LocationRepository;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public class LocationRepositoryGateway implements LocationGateway {

    private final LocationMapper locationMapper;

    private final LocationRepository locationRepository;

    public LocationRepositoryGateway(final LocationMapper locationMapper, final LocationRepository locationRepository) {
        this.locationMapper = locationMapper;
        this.locationRepository = locationRepository;
    }

    @Override
    public Location save(@NotNull final Location location) {

        final LocationEntity entity = locationMapper.toEntity(location);

        final LocationEntity savedLocation = locationRepository.save(entity);

        return locationMapper.map(savedLocation);
    }

    @Override
    public Location getOneByZipCodeOrCreated(@NotNull final Location location) {

        return Optional.of(location)
                .flatMap(loc -> {

                    final String zipcode = Optional.ofNullable(loc.getZipCode())
                            .map(code -> code.replaceAll("[^a-zA-Z0-9]", ""))
                            .orElse(null);

                    return locationRepository.findOneByZipCode(zipcode)
                            .map(locationMapper::map);
                })
                .orElseGet(() -> {

                    final LocationEntity entity = locationMapper.toEntity(location);

                    return locationMapper.map(locationRepository.save(entity));
                });
    }

}
