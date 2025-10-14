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

    @BeforeEach
    void setUp() {

        final UserGateway userGateway = Mockito.mock(UserGateway.class);

        final RoleGateway roleGateway = Mockito.mock(RoleGateway.class);

        final Map<Long, User> userMap = new HashMap<>();

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

                    userMap.put(user.getId(), user);
                    return user;
                });

        Mockito.when(userGateway.update(Mockito.any(User.class)))
                .thenAnswer(invocation -> {

                    final User user = invocation.getArgument(0);

                    if (user.getId() == null) {
                        user.setId(counter.getAndIncrement());
                    }

                    userMap.put(user.getId(), user);
                    return user;
                });

        Mockito.when(userGateway.findById(Mockito.anyLong()))
                .thenAnswer(invocation -> {

                    final Long id = invocation.getArgument(0);

                    return Optional.ofNullable(userMap.get(id));
                });

        Mockito.when(userGateway.findAllActive(Mockito.anyInt(), Mockito.anyInt()))
                .thenAnswer(invocation -> {

                    final int page = invocation.getArgument(0);
                    final int size = invocation.getArgument(1);

                    final List<User> activeUsers = new ArrayList<>(userMap.values());

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

        final User savedUser = userUseCase.created(user);

        assertEquals("Thiago Depizzol", savedUser.getUsername());
        assertEquals("thiago.depizzol@fiap.com.br", savedUser.getLogin());
        assertEquals("12345678", savedUser.getPassword());

        assertEquals("Dono de restaurante", savedRole.getName());
        assertEquals(RoleType.RESTAURANT_OWNER, savedRole.getType());

    }

    @DisplayName("Atualizar usuário com sucesso")
    @Test
    void testUpdateUser() {

        final Role role = new Role();
        role.setActive(true);
        role.setName("Dono de restaurante");
        role.setType(RoleType.RESTAURANT_OWNER);

        final Role saveRole = roleUseCase.created(role);

        final User user = new User();
        user.setUsername("Thiago Depizzol");
        user.setLogin("thiago.depizzol@fiap.com.br");
        user.setPassword("12345678");
        user.setRole(saveRole);

        final User savedUser = userUseCase.created(user);

        savedUser.setUsername("Thiago Oliveira Depizzol");

        final User updateUser = userUseCase.update(savedUser);

        assertEquals("Thiago Oliveira Depizzol", updateUser.getUsername());
        assertEquals("thiago.depizzol@fiap.com.br", updateUser.getLogin());
        assertEquals("12345678", updateUser.getPassword());

    }

    @DisplayName("Buscar usuário por id")
    @Test
    void testGet() {

        final Role role = new Role();
        role.setActive(true);
        role.setName("Dono de restaurante");
        role.setType(RoleType.RESTAURANT_OWNER);

        final Role saveRole = roleUseCase.created(role);

        final User newUser = new User();
        newUser.setActive(true);
        newUser.setUsername("Thiago Depizzol");
        newUser.setLogin("thiago.depizzol@fiap.com.br");
        newUser.setPassword("12345678");
        newUser.setRole(saveRole);

        final User savedUser = userUseCase.created(newUser);

        final Long id = savedUser.getId();

        final User user = userUseCase.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals("Thiago Depizzol", user.getUsername());

    }

    @DisplayName("Buscar usuário pela paginação")
    @Test
    void testGetAll() {

        final Role restaurantOwner = new Role();
        restaurantOwner.setName("Dono de restaurante");
        restaurantOwner.setType(RoleType.RESTAURANT_OWNER);

        final Role customer = new Role();
        customer.setName("Cliente");
        customer.setType(RoleType.CUSTOMER);

        final Role systemAdmin = new Role();
        systemAdmin.setName("Administrador de sistema");
        systemAdmin.setType(RoleType.SYSTEM_ADMIN);

        final Role savedRestaurantOwner = roleUseCase.created(restaurantOwner);
        final Role savedCustomer = roleUseCase.created(customer);
        final Role savedSystemAdmin = roleUseCase.created(systemAdmin);

        final User restaurantOwnerUser = new User();
        restaurantOwnerUser.setUsername("Thiago Depizzol");
        restaurantOwnerUser.setLogin("thiago.depizzol@fiap.com.br");
        restaurantOwnerUser.setPassword("12345678");
        restaurantOwnerUser.setRole(savedRestaurantOwner);

        final User customerUser = new User();
        customerUser.setUsername("Maria Silva");
        customerUser.setLogin("maria.silva@fiap.com.br");
        customerUser.setPassword("87654321");
        customerUser.setRole(savedCustomer);

        final User systemAdminUser = new User();
        systemAdminUser.setUsername("João Souza");
        systemAdminUser.setLogin("joao.souza@fiap.com.br");
        systemAdminUser.setPassword("abcdef12");
        systemAdminUser.setRole(savedSystemAdmin);

        userUseCase.created(restaurantOwnerUser);
        userUseCase.created(customerUser);
        userUseCase.created(systemAdminUser);

        final int page = 0;
        final int size = 10;

        final List<User> users = userUseCase.findAllActive(page, size);

        assertEquals(3, users.size());
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("Thiago Depizzol")));
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("Maria Silva")));
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("João Souza")));

    }

}
