package com.app.domain.entity.user;

import com.app.core.domain.user.User;
import com.app.core.gateways.user.UserGateway;
import com.app.core.usecases.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    private UserUseCase userUseCase;

    private final Long userId = 1L;

    @BeforeEach
    void setUp() {

        final UserGateway gateway = Mockito.mock(UserGateway.class);

        final Map<Long, User> db = new HashMap<>();

        final AtomicLong counter = new AtomicLong(1L);

        Mockito.when(gateway.save(Mockito.any(User.class)))
                .thenAnswer(invocation -> {

                    final User user = invocation.getArgument(0);

                    if (user.getId() == null) {
                        user.setId(counter.getAndIncrement());
                    }

                    db.put(user.getId(), user);
                    return user;
                });

        Mockito.when(gateway.findById(userId))
                .thenAnswer(invocation -> {

                    final Long id = invocation.getArgument(0);

                    return Optional.ofNullable(db.get(id));
                });

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

    @DisplayName("Buscar usuário por id")
    @Test
    void testGet() {

        final User newUser = new User("Thiago Depizzol", "thiago.depizzol@fiap.com.br", "12345678");

        final User savedUser = userUseCase.save(newUser);
        final Long id = savedUser.getId();

        final User user = userUseCase.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals("Thiago Depizzol", user.getUsername());

    }

}
