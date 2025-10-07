package com.app.infra.application.mapper.location;

import com.app.core.domain.location.Location;
import com.app.infra.application.request.location.LocationRequest;
import com.app.infra.entity.location.LocationEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public Location mapToLocation(@NotNull final LocationRequest request) {

        return new Location(
                request.getAddress(),
                request.getNumber(),
                request.getNeighborhood(),
                request.getComplement(),
                request.getZipCode(),
                request.getCity(),
                request.getState()
        );
    }

    public Location mapToLocation(@NotNull final LocationEntity entity) {

        return new Location(
                entity.getId(),
                entity.getAddress(),
                entity.getNumber(),
                entity.getNeighborhood(),
                entity.getComplement(),
                entity.getZipCode(),
                entity.getCity(),
                entity.getState()
        );
    }

    public LocationEntity toEntity(@NotNull final Location location) {

        return new LocationEntity(
                location.getId(),
                location.getAddress(),
                location.getNumber(),
                location.getNeighborhood(),
                location.getComplement(),
                location.getZipCode(),
                location.getCity(),
                location.getState()
        );
    }

    public LocationEntity toEntity(@NotNull final Location location, @NotNull final String zipcode) {

        return new LocationEntity(
                location.getId(),
                location.getAddress(),
                location.getNumber(),
                location.getNeighborhood(),
                location.getComplement(),
                zipcode,
                location.getCity(),
                location.getState()
        );
    }

}
