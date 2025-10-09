package com.app.infra.application;

import com.app.core.domain.enums.RoleType;
import com.app.core.domain.role.Role;
import com.app.core.domain.user.User;
import com.app.core.usecases.roles.RoleUseCase;
import com.app.core.usecases.user.UserUseCase;
import com.app.infra.application.mapper.roles.RoleMapper;
import com.app.infra.repository.roles.RoleRepository;
import com.app.infra.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final UserUseCase userUseCase;

    private final UserRepository userRepository;

    private final RoleUseCase roleUseCase;

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public StartupRunner(final UserUseCase userUseCase, final UserRepository userRepository, final RoleUseCase roleUseCase, final RoleRepository roleRepository, final RoleMapper roleMapper) {
        this.userUseCase = userUseCase;
        this.userRepository = userRepository;
        this.roleUseCase = roleUseCase;
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public void run(String... args) {

        final String email = "admin@fiap.com.br";

        roleRepository.findOneByActive()
                .ifPresentOrElse(entity -> {

                            final Role role = roleMapper.mapToRole(entity);


                            if (userRepository.findByEmail(email).isEmpty()) {

                                final User userAdmin = new User(
                                        null,
                                        "Administrador Administrador",
                                        email,
                                        "admin",
                                        role
                                );

                                userUseCase.created(userAdmin);
                            }
                        },
                        () -> {

                            final Role newRole = new Role(
                                    null,
                                    "System admin",
                                    RoleType.SYSTEM_ADMIN,
                                    "Perfil de acesso do administrado do sistema"
                            );

                            final Role savedRole = roleUseCase.save(newRole);

                            if (userRepository.findByEmail(email).isEmpty()) {

                                final User userAdmin = new User(
                                        null,
                                        "Administrador Administrador",
                                        email,
                                        "admin",
                                        savedRole
                                );

                                userUseCase.created(userAdmin);


                            }
                        });


    }
}
