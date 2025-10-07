package com.app.core.gateways.location;

import com.app.core.domain.location.Location;

import java.util.Optional;

public interface LocationGateway {

    Location save(Location location);

    Optional<Location> findOneByZipCode(String zipcode);

}
