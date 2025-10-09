package com.app.core.usecases.location;

import com.app.core.domain.location.Location;
import com.app.core.gateways.location.LocationGateway;

public class LocationUseCase {

    private final LocationGateway locationGateway;

    public LocationUseCase(LocationGateway locationGateway) {
        this.locationGateway = locationGateway;
    }

    public Location save(final Location location) {

        return locationGateway.save(location);
    }
}
