package com.app.infra.repository.location;

import com.app.infra.entity.location.LocationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<LocationEntity, Long> {

    @Query(nativeQuery = true, //
            value = "select locations.* " +//
                    "from loc_locations locations " +//
                    "where locations.zip_code = :zipCode " +//
                    "limit 1 ")
    Optional<LocationEntity> findOneByZipCode(@Param("zipCode") String zipCode);

}


