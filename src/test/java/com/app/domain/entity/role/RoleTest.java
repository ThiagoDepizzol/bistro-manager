package com.app.domain.entity.role;

import com.app.core.domain.enums.RoleType;
import com.app.core.domain.role.Role;
import com.app.core.gateways.roles.RoleGateway;
import com.app.core.usecases.roles.RoleUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    private RoleUseCase roleUseCase;

    @BeforeEach
    void setUp() {

        final RoleGateway roleGateway = Mockito.mock(RoleGateway.class);

        final Map<Long, Role> rolerMap = new HashMap<>();

        final AtomicLong counter = new AtomicLong(1L);

        Mockito.when(roleGateway.created(Mockito.any(Role.class)))
                .thenAnswer(invocation -> {

                    final Role role = invocation.getArgument(0);

                    if (role.getId() == null) {
                        role.setId(counter.getAndIncrement());
                    }

                    rolerMap.put(role.getId(), role);
                    return role;
                });

        Mockito.when(roleGateway.update(Mockito.any(Role.class)))
                .thenAnswer(invocation -> {

                    final Role role = invocation.getArgument(0);

                    if (role.getId() == null) {
                        role.setId(counter.getAndIncrement());
                    }

                    rolerMap.put(role.getId(), role);
                    return role;
                });

        Mockito.when(roleGateway.findById(Mockito.anyLong()))
                .thenAnswer(invocation -> {

                    final Long id = invocation.getArgument(0);

                    return Optional.ofNullable(rolerMap.get(id));
                });

        Mockito.when(roleGateway.findAllActive(Mockito.anyInt(), Mockito.anyInt()))
                .thenAnswer(invocation -> {

                    final int page = invocation.getArgument(0);
                    final int size = invocation.getArgument(1);

                    final List<Role> activeRoles = new ArrayList<>(rolerMap.values());

                    final int fromIndex = page * size;
                    final int toIndex = Math.min(fromIndex + size, activeRoles.size());

                    if (fromIndex >= activeRoles.size()) {
                        return Collections.emptyList();
                    }

                    return activeRoles.subList(fromIndex, toIndex);

                });

        roleUseCase = new RoleUseCase(roleGateway);
    }

    @DisplayName("Criar perfil de acesso com sucesso")
    @Test
    void testCreateRole() {

        final Role role = new Role();
        role.setActive(true);
        role.setName("Dono de restaurante");
        role.setType(RoleType.RESTAURANT_OWNER);

        final Role savedRole = roleUseCase.created(role);

        assertEquals("Dono de restaurante", savedRole.getName());
        assertEquals(RoleType.RESTAURANT_OWNER, savedRole.getType());

    }

    @DisplayName("Atualizar perfil de acesso com sucesso")
    @Test
    void testUpdateUser() {

        final Role role = new Role();
        role.setActive(true);
        role.setName("Dono de restaurante");
        role.setType(RoleType.RESTAURANT_OWNER);

        final Role savedRole = roleUseCase.created(role);

        savedRole.setName("Donos de restaurantes");

        final Role updateRole = roleUseCase.update(savedRole);

        assertEquals("Donos de restaurantes", updateRole.getName());
        assertEquals(RoleType.RESTAURANT_OWNER, updateRole.getType());

    }

    @DisplayName("Buscar perfil de acesso por id")
    @Test
    void testGet() {

        final Role newRole = new Role();
        newRole.setActive(true);
        newRole.setName("Dono de restaurante");
        newRole.setType(RoleType.RESTAURANT_OWNER);

        final Role savedRole = roleUseCase.created(newRole);

        final Long id = savedRole.getId();

        final Role role = roleUseCase.findById(id)
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        assertNotNull(role);
        assertEquals(id, role.getId());
        assertEquals("Dono de restaurante", role.getName());

    }

    @DisplayName("Buscar perfil de acesso pela paginação")
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

        roleUseCase.created(restaurantOwner);
        roleUseCase.created(customer);
        roleUseCase.created(systemAdmin);

        final int page = 0;
        final int size = 10;

        final List<Role> roles = roleUseCase.findAllActive(page, size);

        assertEquals(3, roles.size());
        assertTrue(roles.stream().anyMatch(role -> role.getName().equals("Dono de restaurante")));
        assertTrue(roles.stream().anyMatch(role -> role.getName().equals("Cliente")));
        assertTrue(roles.stream().anyMatch(role -> role.getName().equals("Administrador de sistema")));

    }

}
