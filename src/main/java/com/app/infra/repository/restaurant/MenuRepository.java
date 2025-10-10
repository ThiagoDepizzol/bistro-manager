package com.app.infra.repository.restaurant;

import com.app.infra.entity.restaurant.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends CrudRepository<MenuEntity, Long> {

    @Query(nativeQuery = true, //
            value = "select menus.* " +//
                    "from res_menus menus " +//
                    "         left join res_restaurants restaurants " +//
                    "                   on restaurants.id = menus.res_restaurant_id " +//
                    "                       and restaurants.active = true " +//
                    "         left join usr_users users " +//
                    "                   on restaurants.restaurant_owner_usr_user_id = users.id " +//
                    "                       and users.active = true " +//
                    "where menus.active = true " +//
                    "  and ((:isSystemAdmin = true) " +//
                    "    or (select(exists(select 1 " +//
                    "                      from usr_users sub_users " +//
                    "                               join adm_roles sub_roles " +//
                    "                                    on sub_users.adm_role_id = sub_roles.id " +//
                    "                                        and sub_roles.active = true " +//
                    "                                        and sub_roles.type = 'RESTAURANT_OWNER' " +//
                    "                      where users.id = sub_users.id " +//
                    "                        and sub_users.id = :userId)))) ")
    Page<MenuEntity> findAllByActive(@Param("isSystemAdmin") Boolean isSystemAdmin, @Param("userId") Long userId, Pageable pageable);


    @Query(nativeQuery = true, //
            value = "select menus.* " +//
                    "from res_menus menus " +//
                    "         left join res_restaurants restaurants " +//
                    "                   on restaurants.id = menus.res_restaurant_id " +//
                    "                       and restaurants.active = true " +//
                    "where menus.active = true " +//
                    "  and restaurants.id = :restaurantId ")
    List<MenuEntity> getAllByRestaurant(@Param("restaurantId") Long restaurantId);
}


