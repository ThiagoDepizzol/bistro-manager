package com.app.infra.repository.user;

import com.app.infra.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query(nativeQuery = true, //
            value = "select users.* " +//
                    "from usr_users users " +//
                    "where users.active = true ")
    Page<UserEntity> findAllByActive(Pageable pageable);

    @Query(nativeQuery = true, //
            value = "select users.* " +//
                    "from usr_users users " +//
                    "where users.active = true " +//
                    "  and lower(users.login) = lower(cast(:login as text)) " +//
                    "limit 1 ")
    Optional<UserEntity> findByEmail(@Param("login") String login);

    @Query(nativeQuery = true, //
            value = "select (exists(select users.* " +//
                    "               from usr_users users " +//
                    "                        join adm_roles roles " +//
                    "                             on users.adm_role_id = roles.id " +//
                    "                                 and roles.active = true " +//
                    "               where users.id = :userId " +//
                    "                 and users.active = true " +//
                    "                 and roles.type in (:types))) ")
    Boolean userHasPermission(@Param("userId") Long userId, @Param("types") List<String> types);
}

