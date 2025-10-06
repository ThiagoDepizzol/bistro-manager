package com.app.infra.repository.user;

import com.app.infra.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Page<UserEntity> findAllByActive(Pageable pageable);
}

