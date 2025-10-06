package com.app.infra.gateway.user;

import com.app.core.domain.user.User;
import com.app.core.gateways.user.UserGateway;
import com.app.infra.controller.user.mapper.UserMapper;
import com.app.infra.entity.user.UserEntity;
import com.app.infra.repository.user.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Optional<User> findById(@NotNull final Long id) {

        final UserEntity savedEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        final User user = userMapper.mapToUser(savedEntity);

        return Optional.of(user);
    }

    @Override
    public List<User> findAllActive(final int page, final int size) {

        final Pageable pageable = PageRequest.of(page, size);

        final Page<UserEntity> entities = userRepository.findAllByActive(pageable);

        return entities
                .stream()
                .map(userMapper::mapToUser)
                .collect(Collectors.toList());
    }
}
