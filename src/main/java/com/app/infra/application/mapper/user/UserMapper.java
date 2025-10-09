package com.app.infra.application.mapper.user;

import com.app.core.domain.user.User;
import com.app.core.utils.PasswordUtils;
import com.app.infra.application.dto.user.UserDTO;
import com.app.infra.application.mapper.roles.RoleMapper;
import com.app.infra.application.request.user.UserRequest;
import com.app.infra.entity.user.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {

    private final RoleMapper roleMapper;

    public UserMapper(final RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public User mapWithoutEncryptPassword(@NotNull final UserRequest request) {

        final User user = new User();

        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setLogin(request.getLogin());

        if (Objects.nonNull(request.getRole())) {

            user.setRole(roleMapper.map(request.getRole()));

        }

        return user;
    }

    public User map(@NotNull final UserRequest request) {

        final User user = new User();

        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setLogin(request.getLogin());
        user.setPassword(PasswordUtils.encryptPassword(request.getPassword()));

        if (Objects.nonNull(request.getRole())) {

            user.setRole(roleMapper.map(request.getRole()));

        }

        return user;
    }

    public User map(@NotNull final UserRequest request, @NotNull final Long id) {

        final User user = new User();

        user.setId(id);
        user.setUsername(request.getUsername());
        user.setLogin(request.getLogin());
        user.setPassword(PasswordUtils.encryptPassword(request.getPassword()));

        if (Objects.nonNull(request.getRole())) {

            user.setRole(roleMapper.map(request.getRole()));

        }

        return user;
    }

    public User map(@NotNull final UserEntity entity) {

        final User user = new User();

        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setLogin(entity.getLogin());
        user.setPassword(entity.getPassword());

        if (Objects.nonNull(entity.getRole())) {

            user.setRole(roleMapper.map(entity.getRole()));

        }

        return user;
    }

    public UserEntity toEntity(@NotNull final User user) {

        final UserEntity entity = new UserEntity();

        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setLogin(user.getLogin());
        entity.setPassword(user.getPassword());

        if (Objects.nonNull(user.getRole())) {

            entity.setRole(roleMapper.toEntity(user.getRole()));

        }

        return entity;
    }

    public UserDTO toDTO(@NotNull final User user) {

        final UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        if (Objects.nonNull(user.getRole())) {

            dto.setRole(roleMapper.toDTO(user.getRole()));

        }

        return dto;
    }

}
