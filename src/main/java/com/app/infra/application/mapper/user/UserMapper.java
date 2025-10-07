package com.app.infra.application.mapper.user;

import com.app.core.domain.user.User;
import com.app.core.utils.PasswordUtils;
import com.app.infra.application.dto.user.UserDTO;
import com.app.infra.application.request.user.UserRequest;
import com.app.infra.entity.user.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(@NotNull final UserRequest json) {
        return new User(json.getId(), json.getUsername(), json.getLogin(), PasswordUtils.encryptPassword(json.getPassword()));
    }

    public User mapToUser(@NotNull final UserRequest json, @NotNull final Long id) {
        return new User(id, json.getUsername(), json.getLogin(), json.getPassword());
    }

    public User mapToUser(@NotNull final UserEntity entity) {
        return new User(entity.getId(), entity.getUsername(), entity.getLogin(), entity.getPassword());
    }

    public UserEntity toEntity(@NotNull final User user) {
        return new UserEntity(user.getUsername(), user.getLogin(), user.getPassword());
    }

    public UserDTO mapToDTO(@NotNull final User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }


}
