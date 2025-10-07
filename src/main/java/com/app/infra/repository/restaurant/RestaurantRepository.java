package com.app.infra.repository.restaurant;

import com.app.infra.entity.restaurant.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Long> {

    @Query(nativeQuery = true, //
            value = "select restaurants.* " +//
                    "from res_restaurants restaurants " +//
                    "where restaurants.active = true ")
    Page<RestaurantEntity> findAllByActive(Pageable pageable);
}


