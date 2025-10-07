package com.app.infra.repository.user;

import com.app.infra.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Page<UserEntity> findAllByActive(Pageable pageable);

    @Query(nativeQuery = true, //
            value = "select users.* " +//
                    "from usr_users users " +//
                    "where users.active = true " +//
                    "  and lower(users.login) = lower(cast(:login as text)) " +//
                    "limit 1 ")
    Optional<UserEntity> findByEmail(@Param("login") String login);
}

