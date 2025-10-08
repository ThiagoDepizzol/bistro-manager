package com.app.domain.entity.user;

import com.app.core.domain.enums.RoleType;
import com.app.core.domain.role.Role;
import com.app.core.domain.user.User;
import com.app.core.gateways.roles.RoleGateway;
import com.app.core.gateways.user.UserGateway;
import com.app.core.usecases.roles.RoleUseCase;
import com.app.core.usecases.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private UserUseCase userUseCase;

    private RoleUseCase roleUseCase;

    private final Long userId = 1L;

    @BeforeEach
    void setUp() {

        final UserGateway userGateway = Mockito.mock(UserGateway.class);

        final RoleGateway roleGateway = Mockito.mock(RoleGateway.class);

        final Map<Long, User> db = new HashMap<>();

        final AtomicLong counter = new AtomicLong(1L);

        Mockito.when(userGateway.save(Mockito.any(User.class)))
                .thenAnswer(invocation -> {

                    final User user = invocation.getArgument(0);

                    if (user.getId() == null) {
                        user.setId(counter.getAndIncrement());
                    }

                    db.put(user.getId(), user);
                    return user;
                });

        Mockito.when(userGateway.findById(userId))
                .thenAnswer(invocation -> {

                    final Long id = invocation.getArgument(0);

                    return Optional.ofNullable(db.get(id));
                });

        Mockito.when(userGateway.findAllActive(Mockito.anyInt(), Mockito.anyInt()))
                .thenAnswer(invocation -> {

                    final int page = invocation.getArgument(0);
                    final int size = invocation.getArgument(1);

                    final List<User> activeUsers = new ArrayList<>(db.values());

                    final int fromIndex = page * size;
                    final int toIndex = Math.min(fromIndex + size, activeUsers.size());

                    if (fromIndex >= activeUsers.size()) {
                        return Collections.emptyList();
                    }

                    return activeUsers.subList(fromIndex, toIndex);

                });

        userUseCase = new UserUseCase(userGateway);
        roleUseCase = new RoleUseCase(roleGateway);
    }

    @DisplayName("Cria usuário com sucesso")
    @Test
    void testCreateUser() {

        final Role role = new Role(null, "Dono de restaurante", RoleType.RESTAURANT_OWNER, null);

        final Role saveRole = roleUseCase.save(role);

        final User user = new User(null, "Thiago Depizzol", "thiago.depizzol@fiap.com.br", "12345678", saveRole);

        final User saveUser = userUseCase.save(user);

        assertEquals("Thiago Depizzol", saveUser.getUsername());
        assertEquals("thiago.depizzol@fiap.com.br", saveUser.getLogin());
        assertEquals("12345678", saveUser.getPassword());

        assertEquals("Dono de restaurante", saveRole.getName());
        assertEquals(RoleType.RESTAURANT_OWNER, saveRole.getType());

    }

    @DisplayName("Atualizar usuário com sucesso")
    @Test
    void testUpdateUser() {

        final Role role = new Role(null, "Dono de restaurante", RoleType.RESTAURANT_OWNER, null);

        final Role saveRole = roleUseCase.save(role);

        final User user = new User(null, "Thiago Depizzol", "thiago.depizzol@fiap.com.br", "12345678", saveRole);

        final User saveUser = userUseCase.save(user);

        saveUser.setUsername("Thiago Oliveira Depizzol");

        final User updateUser = userUseCase.save(saveUser);

        assertEquals("Thiago Oliveira Depizzol", updateUser.getUsername());
        assertEquals("thiago.depizzol@fiap.com.br", updateUser.getLogin());
        assertEquals("12345678", updateUser.getPassword());

    }

    @DisplayName("Buscar usuário por id")
    @Test
    void testGet() {

        final Role role = new Role(null, "Dono de restaurante", RoleType.RESTAURANT_OWNER, null);

        final Role saveRole = roleUseCase.save(role);

        final User newUser = new User(null, "Thiago Depizzol", "thiago.depizzol@fiap.com.br", "12345678", saveRole);

        final User saveUser = userUseCase.save(newUser);

        final User savedUser = userUseCase.save(saveUser);

        final Long id = savedUser.getId();

        final User user = userUseCase.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals("Thiago Depizzol", user.getUsername());

    }

    @DisplayName("Buscar usuário pela paginação")
    @Test
    void testGetAll() {
        final Role restaurantOwner = roleUseCase.save(new Role(null, "Dono de restaurante", RoleType.RESTAURANT_OWNER, null));
        final Role customer = roleUseCase.save(new Role(null, "Dono de restaurante", RoleType.CUSTOMER, null));
        final Role systemAdmin = roleUseCase.save(new Role(null, "Dono de restaurante", RoleType.SYSTEM_ADMIN, null));

        userUseCase.save(new User(null, "Thiago Depizzol", "thiago.depizzol@fiap.com.br", "12345678", restaurantOwner));
        userUseCase.save(new User(null, "Maria Silva", "maria.silva@fiap.com.br", "87654321", customer));
        userUseCase.save(new User(null, "João Souza", "joao.souza@fiap.com.br", "abcdef12", systemAdmin));

        final int page = 0;
        final int size = 10;

        final List<User> users = userUseCase.findAllActive(page, size);

        assertEquals(3, users.size());
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("Thiago Depizzol")));
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("Maria Silva")));
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("João Souza")));

    }

}
