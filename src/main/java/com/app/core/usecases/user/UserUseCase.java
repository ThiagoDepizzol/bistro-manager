package com.app.core.usecases.user;

import com.app.core.domain.user.User;
import com.app.core.gateways.user.UserGateway;

public class UserUseCase {

    private final UserGateway userGateway;

    public UserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User create(User user) {

        return userGateway.create(user);
    }
}
