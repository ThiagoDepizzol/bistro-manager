package com.app.infra.application.validator.user;

import com.app.core.domain.role.Role;
import com.app.core.domain.user.User;
import com.app.core.exception.DomainException;
import com.app.infra.repository.roles.RoleRepository;
import com.app.infra.repository.user.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserValidator {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserValidator(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void validateUserCreation(User user) {
        validateEmailUniqueness(user);
        validateRole(user);
    }

    public void validateUserUpdate(User user) {
        validateRole(user);
    }

    private void validateEmailUniqueness(User user) {
        if (userRepository.findByEmail(user.getLogin()).isPresent()) {
            throw new DomainException("Email já registrado no sistema");
        }
    }

    private void validateRole(User user) {
        if (Objects.nonNull(user.getRole())) {
            final Long roleId = Optional.ofNullable(user.getRole())
                    .map(Role::getId)
                    .orElseThrow(() -> new DomainException("Perfil de acesso não encontrado"));

            roleRepository.findById(roleId)
                    .orElseThrow(() -> new DomainException("Perfil de acesso não cadastrado no sistema"));
        }
    }

}
