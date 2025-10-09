package com.app.core.gateways.user;

import com.app.core.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserGateway {

    User created(User user);

    User update(User user);

    Optional<User> findById(Long id);

    List<User> findAllActive(int page, int size);
}
