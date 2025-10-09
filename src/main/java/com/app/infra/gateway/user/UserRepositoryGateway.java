package com.app.infra.gateway.user;

import com.app.core.domain.user.User;
import com.app.core.exception.DomainException;
import com.app.core.gateways.user.UserGateway;
import com.app.infra.application.mapper.user.UserMapper;
import com.app.infra.application.validator.user.UserValidator;
import com.app.infra.entity.user.UserEntity;
import com.app.infra.repository.user.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepositoryGateway implements UserGateway {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryGateway.class);

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

        final boolean hasRole = Objects.nonNull(user.getRole());

        final UserEntity userEntity = hasRole ?
                userMapper.toNewEntityWithRole(user) :
                userMapper.toNewEntityWithoutRole(user);

        final UserEntity savedEntity = userRepository.save(userEntity);

        return userMapper.mapToUser(savedEntity);
    }

    @Override
    public User update(@NotNull final User user) {

        userValidator.validateUserUpdate(user);

        final boolean hasRole = Objects.nonNull(user.getRole());

        final UserEntity userEntity = hasRole ?
                userMapper.toNewEntityWithRole(user) :
                userMapper.toNewEntityWithoutRole(user);

        final UserEntity savedEntity = userRepository.save(userEntity);

        return userMapper.mapToUser(savedEntity);
    }

    @Override
    public User save(@NotNull final User user) {

        final UserEntity userEntity = userMapper.toNewEntityWithRole(user);

        final UserEntity savedEntity = userRepository.save(userEntity);

        return userMapper.mapToUser(savedEntity);
    }

    @Override
    public Optional<User> findById(@NotNull final Long id) {

        final UserEntity savedEntity = userRepository.findById(id)
                .orElseThrow(() -> new DomainException("Usuário não encontrado"));

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
