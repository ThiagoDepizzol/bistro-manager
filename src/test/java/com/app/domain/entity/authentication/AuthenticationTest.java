package com.app.domain.entity.authentication;

import com.app.core.domain.enums.RoleType;
import com.app.core.domain.role.Role;
import com.app.core.domain.user.User;
import com.app.core.gateways.authentication.AuthenticationGateway;
import com.app.core.gateways.roles.RoleGateway;
import com.app.core.gateways.user.UserGateway;
import com.app.core.usecases.authentication.AuthenticationUseCase;
import com.app.core.usecases.roles.RoleUseCase;
import com.app.core.usecases.user.UserUseCase;
import com.app.infra.application.dto.authentication.LoginDTO;
import com.app.infra.application.mapper.user.UserMapper;
import com.app.infra.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationTest {

    private UserUseCase userUseCase;

    private RoleUseCase roleUseCase;

    private AuthenticationUseCase authenticationUseCase;

    private AuthenticationGateway authenticationGateway;

    private UserRepository userRepository;

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {

        UserGateway userGateway = Mockito.mock(UserGateway.class);

        RoleGateway roleGateway = Mockito.mock(RoleGateway.class);

        authenticationGateway = Mockito.mock(AuthenticationGateway.class);

        userRepository = Mockito.mock(UserRepository.class);

        userMapper = Mockito.mock(UserMapper.class);

        final AtomicLong counter = new AtomicLong(1L);

        Mockito.when(roleGateway.created(Mockito.any(Role.class)))
                .thenAnswer(invocation -> {
                    final Role role = invocation.getArgument(0);
                    if (role.getId() == null) {
                        role.setId(counter.getAndIncrement());
                    }
                    return role;
                });

        Mockito.when(userGateway.created(Mockito.any(User.class)))
                .thenAnswer(invocation -> {
                    final User user = invocation.getArgument(0);
                    if (user.getId() == null) {
                        user.setId(counter.getAndIncrement());
                    }
                    return user;
                });

        Mockito.doNothing()
                .when(authenticationGateway)
                .authentication(Mockito.any(LoginDTO.class));

        Mockito.doNothing()
                .when(authenticationGateway)
                .disconnect(Mockito.any(String.class), Mockito.any(String.class));

        userUseCase = new UserUseCase(userGateway);
        roleUseCase = new RoleUseCase(roleGateway);
        authenticationUseCase = new AuthenticationUseCase(authenticationGateway);
    }

    @DisplayName("Usuário logado com sucesso")
    @Test
    void testUserLogin() {

        final Role role = new Role();
        role.setActive(true);
        role.setName("Dono de restaurante");
        role.setType(RoleType.RESTAURANT_OWNER);

        final Role savedRole = roleUseCase.created(role);

        final User user = new User();
        user.setActive(true);
        user.setUsername("Thiago Depizzol");
        user.setLogin("thiago.depizzol@fiap.com.br");
        user.setPassword("12345678");
        user.setRole(savedRole);

        userUseCase.created(user);

        final LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("thiago.depizzol@fiap.com.br");
        loginDTO.setPassword("12345678");

        authenticationUseCase.authentication(loginDTO);

        Mockito.verify(authenticationGateway, Mockito.times(1))
                .authentication(Mockito.eq(loginDTO));
    }

    @DisplayName("Usuário com credenciais inválidas não consegue logar")
    @Test
    void testUserLoginFailed() {

        final Role role = new Role();
        role.setActive(true);
        role.setName("Dono de restaurante");
        role.setType(RoleType.RESTAURANT_OWNER);

        final Role savedRole = roleUseCase.created(role);

        final User user = new User();
        user.setActive(true);
        user.setUsername("Thiago Depizzol");
        user.setLogin("thiago.depizzol@fiap.com.br");
        user.setPassword("12345678");
        user.setRole(savedRole);

        userUseCase.created(user);

        final LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("thiago.depizzol@fiap.com.br");
        loginDTO.setPassword("987654321");

        Mockito.doThrow(new RuntimeException("Credenciais inválidas"))
                .when(authenticationGateway)
                .authentication(Mockito.any(LoginDTO.class));

        assertThrows(RuntimeException.class,
                () -> authenticationUseCase.authentication(loginDTO),
                "Esperado erro de credenciais inválidas"
        );

        Mockito.verify(authenticationGateway, Mockito.times(1))
                .authentication(Mockito.eq(loginDTO));
    }

    @Test
    @DisplayName("Desconecta usuário com sucesso")
    void testDisconnectSuccess() {

        final Role role = new Role();
        role.setActive(true);
        role.setName("Dono de restaurante");
        role.setType(RoleType.RESTAURANT_OWNER);

        final Role savedRole = roleUseCase.created(role);

        final User user = new User();
        user.setActive(true);
        user.setUsername("Thiago Depizzol");
        user.setLogin("thiago.depizzol@fiap.com.br");
        user.setPassword("12345678");
        user.setRole(savedRole);

        userUseCase.created(user);

        final LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("thiago.depizzol@fiap.com.br");
        loginDTO.setPassword("123");

        authenticationUseCase.disconnect(loginDTO.getLogin(), loginDTO.getPassword());

        Mockito.verify(authenticationGateway)
                .disconnect(Mockito.eq(loginDTO.getLogin()), Mockito.eq(loginDTO.getPassword()));

    }

}
