package com.app.core.gateways.user;

import com.app.core.domain.user.User;

import java.util.Optional;

public interface UserGateway {

    User save(User user);

    Optional<User> findById(Long id);
}
