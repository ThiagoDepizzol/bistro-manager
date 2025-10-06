package com.app.infra.controller.user.mapper;

import com.app.core.domain.user.User;
import com.app.infra.controller.user.dto.UserDTO;
import com.app.infra.controller.user.json.UserJson;
import com.app.infra.entity.user.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(@NotNull final UserJson json) {
        return new User(json.getId(), json.getUsername(), json.getLogin(), json.getPassword());
    }

    public User mapToUser(@NotNull final UserEntity entity) {
        return new User(entity.getId(), entity.getUsername(), entity.getLogin(), entity.getPassword());
    }

    public UserEntity toEntity(@NotNull final User user) {
        return new UserEntity();
    }

    public UserDTO mapToDTO(@NotNull final User user) {
        return new UserDTO();
    }


}
