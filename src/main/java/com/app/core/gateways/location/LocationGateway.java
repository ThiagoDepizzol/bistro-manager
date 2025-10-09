package com.app.core.gateways.location;

import com.app.core.domain.location.Location;

public interface LocationGateway {

    Location save(Location location);

    Location getOneByZipCodeOrCreated(Location location);

}
