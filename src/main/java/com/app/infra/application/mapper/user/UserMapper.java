package com.app.infra.application.mapper.user;

import com.app.core.domain.role.Role;
import com.app.core.domain.user.User;
import com.app.core.utils.PasswordUtils;
import com.app.infra.application.dto.user.UserDTO;
import com.app.infra.application.mapper.roles.RoleMapper;
import com.app.infra.application.request.user.UserRequest;
import com.app.infra.entity.roles.RoleEntity;
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

    public User mapToUser(@NotNull final UserRequest request) {


        if (Objects.nonNull(request.getRole())) {

            final Role role = roleMapper.mapToRole(request.getRole());

            return new User(
                    request.getUsername(),
                    request.getLogin(),
                    PasswordUtils.encryptPassword(request.getPassword()),
                    role
            );

        } else {

            return new User(
                    request.getUsername(),
                    request.getLogin(),
                    PasswordUtils.encryptPassword(request.getPassword())
            );

        }


    }

    public User mapToUser(@NotNull final UserRequest request, @NotNull final Long id) {

        final Role role = roleMapper.mapToRole(request.getRole());

        return new User(
                id,
                request.getUsername(),
                request.getLogin(),
                PasswordUtils.encryptPassword(request.getPassword()),
                role
        );
    }

    public User mapToUser(@NotNull final UserEntity entity) {

        if (Objects.nonNull(entity.getRole())) {

            final Role role = roleMapper.mapToRole(entity.getRole());

            return new User(
                    entity.getId(),
                    entity.getUsername(),
                    entity.getLogin(),
                    entity.getPassword(),
                    role
            );

        } else {

            return new User(
                    entity.getId(),
                    entity.getUsername(),
                    entity.getLogin(),
                    entity.getPassword()
            );
        }

    }

    public UserEntity toNewEntityWithoutRole(@NotNull final User user) {

        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getLogin(),
                user.getPassword()
        );

    }

    public UserEntity toNewEntityWithRole(@NotNull final User user) {

        final RoleEntity role = roleMapper.toEntity(user.getRole());

        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getLogin(),
                user.getPassword(),
                role
        );

    }

    public UserDTO mapToDTO(@NotNull final User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername()
        );
    }


}
