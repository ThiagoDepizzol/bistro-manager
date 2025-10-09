package com.app.infra.gateway.user;

import com.app.core.domain.user.User;
import com.app.core.exception.DomainException;
import com.app.core.gateways.user.UserGateway;
import com.app.infra.application.mapper.user.UserMapper;
import com.app.infra.application.validator.user.UserValidator;
import com.app.infra.entity.user.UserEntity;
import com.app.infra.repository.user.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepositoryGateway implements UserGateway {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final UserValidator userValidator;

    public UserRepositoryGateway(final UserMapper userMapper, final UserRepository userRepository, final UserValidator userValidator) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public User created(@NotNull final User user) {

        userValidator.validateUserCreation(user);

        final UserEntity userEntity = userMapper.toEntity(user);

        final UserEntity createdEntity = userRepository.save(userEntity);

        return userMapper.map(createdEntity);
    }

    @Override
    public User update(@NotNull final User user) {

        userValidator.validateUserUpdate(user);

        final UserEntity userEntity = userMapper.toEntity(user);

        final UserEntity updateEntity = userRepository.save(userEntity);

        return userMapper.map(updateEntity);
    }

    @Override
    public Optional<User> findById(@NotNull final Long id) {

        final UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new DomainException("Usuário não encontrado"));

        final User user = userMapper.map(entity);

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAllActive(final int page, final int size) {

        final Pageable pageable = PageRequest.of(page, size);

        return userRepository.findAllByActive(pageable)
                .stream()
                .map(userMapper::map)
                .collect(Collectors.toList());
    }
}
