package com.app.domain.entity.user;

import com.app.core.domain.user.User;
import com.app.core.gateways.user.UserGateway;
import com.app.core.usecases.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {

        final UserGateway gateway = Mockito.mock(UserGateway.class);

        Mockito.when(gateway.save(Mockito.any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        userUseCase = new UserUseCase(gateway);
    }

    @DisplayName("Cria usuário com sucesso")
    @Test
    void testCreateUser() {

        final User user = new User("Thiago Depizzol", "thiago.depizzol@fiap.com.br", "12345678");

        final User saveUser = userUseCase.save(user);

        assertEquals("Thiago Depizzol", saveUser.getUsername());
        assertEquals("thiago.depizzol@fiap.com.br", saveUser.getLogin());
        assertEquals("12345678", saveUser.getPassword());

    }

    @DisplayName("Atualizar usuário com sucesso")
    @Test
    void testUpdateUser() {

        final User newUser = new User("Thiago Depizzol", "thiago.depizzol@fiap.com.br", "12345678");

        final User saveUser = userUseCase.save(newUser);

        saveUser.setUsername("Thiago Oliveira Depizzol");

        final User updateUser = userUseCase.save(saveUser);

        assertEquals("Thiago Oliveira Depizzol", updateUser.getUsername());
        assertEquals("thiago.depizzol@fiap.com.br", updateUser.getLogin());
        assertEquals("12345678", updateUser.getPassword());

    }

}
