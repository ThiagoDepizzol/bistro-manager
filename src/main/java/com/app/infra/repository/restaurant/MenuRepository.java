package com.app.infra.repository.restaurant;

import com.app.infra.entity.restaurant.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<MenuEntity, Long> {

    @Query(nativeQuery = true, //
            value = "select menus.* " +//
                    "from res_menus menus " +//
                    "where menus.active = true ")
    Page<MenuEntity> findAllByActive(Pageable pageable);
}


