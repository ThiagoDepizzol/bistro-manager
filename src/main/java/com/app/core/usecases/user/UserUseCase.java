package com.app.core.usecases.user;

import com.app.core.domain.user.User;
import com.app.core.gateways.user.UserGateway;

import java.util.Optional;

public class UserUseCase {

    private final UserGateway userGateway;

    public UserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User save(final User user) {

        return userGateway.save(user);
    }

    public Optional<User> findById(final Long id) {

        return userGateway.findById(id);
    }
}
