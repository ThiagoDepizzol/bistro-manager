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

        final String zipLocation = Optional.ofNullable(location.getZipCode())
                .map(zipCode -> zipCode.replaceAll("[^a-zA-Z0-9]", ""))
                .orElse(null);

        final LocationEntity entity = locationMapper.toEntity(location, zipLocation);

        return locationMapper.mapToLocation(entity);
    }

    @Override
    public Optional<Location> findOneByZipCode(@NotNull final String zipCode) {

        final String zipLocation = Optional.ofNullable(zipCode)
                .map(code -> code.replaceAll("[^a-zA-Z0-9]", ""))
                .orElse(null);

        final LocationEntity entity = locationRepository.findOneByZipCode(zipLocation)
                .orElseThrow(() -> new IllegalStateException("Location not found by zip code"));

        return Optional.ofNullable(locationMapper.mapToLocation(entity));
    }

}
