package com.app.infra.repository.user;

import com.app.infra.entity.user.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}

