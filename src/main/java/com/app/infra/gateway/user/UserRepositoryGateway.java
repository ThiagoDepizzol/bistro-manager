package com.app.infra.gateway.user;

import com.app.core.domain.user.User;
import com.app.core.gateways.user.UserGateway;
import com.app.infra.controller.user.mapper.UserMapper;
import com.app.infra.entity.user.UserEntity;
import com.app.infra.repository.user.UserRepository;
import jakarta.validation.constraints.NotNull;

public class UserRepositoryGateway implements UserGateway {
    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public UserRepositoryGateway(final UserMapper userMapper, final UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public User save(@NotNull final User user) {

        final UserEntity userEntity = userMapper.toEntity(user);

        final UserEntity savedEntity = userRepository.save(userEntity);

        return userMapper.mapToUser(savedEntity);
    }
}
