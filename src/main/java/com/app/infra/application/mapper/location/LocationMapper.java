package com.app.infra.application.mapper.location;

import com.app.core.domain.location.Location;
import com.app.core.exception.DomainException;
import com.app.infra.application.request.location.LocationRequest;
import com.app.infra.entity.location.LocationEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LocationMapper {

    public Location map(@NotNull final LocationRequest request) {

        final Location location = new Location();

        location.setId(request.getId());
        location.setAddress(request.getAddress());
        location.setNumber(request.getNumber());
        location.setNeighborhood(request.getNeighborhood());
        location.setComplement(request.getComplement());
        location.setZipCode(request.getZipCode());
        location.setCity(request.getCity());
        location.setState(request.getState());

        return location;
    }

    public Location map(@NotNull final LocationEntity entity) {

        final Location location = new Location();

        location.setId(entity.getId());
        location.setAddress(entity.getAddress());
        location.setNumber(entity.getNumber());
        location.setNeighborhood(entity.getNeighborhood());
        location.setComplement(entity.getComplement());
        location.setZipCode(entity.getZipCode());
        location.setCity(entity.getCity());
        location.setState(entity.getState());

        return location;
    }

    public LocationEntity toEntity(@NotNull final Location location) {

        final LocationEntity entity = new LocationEntity();

        entity.setId(location.getId());
        entity.setAddress(location.getAddress());
        entity.setNumber(location.getNumber());
        entity.setNeighborhood(location.getNeighborhood());
        entity.setComplement(location.getComplement());
        entity.setZipCode(Optional.ofNullable(location.getZipCode())
                .map(code -> code.replaceAll("[^a-zA-Z0-9]", ""))
                .orElseThrow(() -> new DomainException("CEP não encontrado")));
        entity.setCity(location.getCity());
        entity.setState(location.getState());

        return entity;
    }

}
