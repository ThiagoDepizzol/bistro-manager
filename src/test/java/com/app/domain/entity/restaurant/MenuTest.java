package com.app.domain.entity.restaurant;

import com.app.core.domain.enums.RoleType;
import com.app.core.domain.location.Location;
import com.app.core.domain.restaurant.Menu;
import com.app.core.domain.restaurant.Restaurant;
import com.app.core.domain.role.Role;
import com.app.core.domain.user.User;
import com.app.core.gateways.location.LocationGateway;
import com.app.core.gateways.restaurant.MenuGateway;
import com.app.core.gateways.restaurant.RestaurantGateway;
import com.app.core.gateways.roles.RoleGateway;
import com.app.core.gateways.user.UserGateway;
import com.app.core.usecases.location.LocationUseCase;
import com.app.core.usecases.restaurant.MenuUseCase;
import com.app.core.usecases.restaurant.RestaurantUseCase;
import com.app.core.usecases.roles.RoleUseCase;
import com.app.core.usecases.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    private MenuUseCase menuUseCase;

    private RestaurantUseCase restaurantUseCase;

    private RoleUseCase roleUseCase;

    private UserUseCase userUseCase;

    private LocationUseCase locationUseCase;

    @BeforeEach
    void setUp() {

        final RestaurantGateway restaurantGateway = Mockito.mock(RestaurantGateway.class);

        final RoleGateway roleGateway = Mockito.mock(RoleGateway.class);

        final UserGateway userGateway = Mockito.mock(UserGateway.class);

        final MenuGateway menuGateway = Mockito.mock(MenuGateway.class);

        final LocationGateway locationGateway = Mockito.mock(LocationGateway.class);

        final Map<Long, Menu> menuMap = new HashMap<>();

        final AtomicLong counter = new AtomicLong(1L);

        Mockito.when(roleGateway.created(Mockito.any(Role.class))).thenAnswer(invocation -> {

            final Role role = invocation.getArgument(0);

            if (role.getId() == null) {
                role.setId(counter.getAndIncrement());
            }

            return role;
        });

        Mockito.when(userGateway.created(Mockito.any(User.class))).thenAnswer(invocation -> {

            final User user = invocation.getArgument(0);

            if (user.getId() == null) {
                user.setId(counter.getAndIncrement());
            }

            return user;
        });

        Mockito.when(locationGateway.save(Mockito.any(Location.class))).thenAnswer(invocation -> {

            final Location location = invocation.getArgument(0);

            if (location.getId() == null) {
                location.setId(counter.getAndIncrement());
            }

            return location;
        });

        Mockito.when(restaurantGateway.created(Mockito.any(Restaurant.class))).thenAnswer(invocation -> {

            final Restaurant restaurant = invocation.getArgument(0);

            if (restaurant.getId() == null) {
                restaurant.setId(counter.getAndIncrement());
            }

            return restaurant;
        });

        Mockito.when(menuGateway.created(Mockito.any(Menu.class))).thenAnswer(invocation -> {

            final Menu menu = invocation.getArgument(0);

            if (menu.getId() == null) {
                menu.setId(counter.getAndIncrement());
            }

            menuMap.put(menu.getId(), menu);
            return menu;
        });

        Mockito.when(menuGateway.update(Mockito.any(Menu.class))).thenAnswer(invocation -> {

            final Menu menu = invocation.getArgument(0);

            if (menu.getId() == null) {
                menu.setId(counter.getAndIncrement());
            }

            menuMap.put(menu.getId(), menu);
            return menu;
        });

        Mockito.when(menuGateway.findById(Mockito.anyLong())).thenAnswer(invocation -> {

            final Long id = invocation.getArgument(0);

            return Optional.ofNullable(menuMap.get(id));
        });

        Mockito.when(menuGateway.findAllActive(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))
                .thenAnswer(invocation -> {

                    final int page = invocation.getArgument(0);
                    final int size = invocation.getArgument(1);

                    final List<Menu> activeRoles = new ArrayList<>(menuMap.values());

                    final int fromIndex = page * size;
                    final int toIndex = Math.min(fromIndex + size, activeRoles.size());

                    if (fromIndex >= activeRoles.size()) {
                        return Collections.emptyList();
                    }

                    return activeRoles.subList(fromIndex, toIndex);

                });

        Mockito.when(menuGateway.getAllByRestaurant(Mockito.anyLong()))
                .thenAnswer(invocation -> new ArrayList<>(menuMap.values()));


        restaurantUseCase = new RestaurantUseCase(restaurantGateway);
        locationUseCase = new LocationUseCase(locationGateway);
        menuUseCase = new MenuUseCase(menuGateway);
        userUseCase = new UserUseCase(userGateway);
        roleUseCase = new RoleUseCase(roleGateway);

    }

    @DisplayName("Criar cardápio com sucesso")
    @Test
    void testCreateMenu() {

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

        final Location location = new Location();
        location.setActive(true);
        location.setAddress("Travessa Raimundo Mesquita");
        location.setNumber("7865");
        location.setNeighborhood("Bahia Velha");
        location.setZipCode("69911-586");
        location.setCity("Rio Branco");
        location.setState("Acrê");

        final Location savedLocation = locationUseCase.save(location);

        final Restaurant restaurant = new Restaurant();
        restaurant.setActive(true);
        restaurant.setName("Restaurante empório Th");
        restaurant.setKitchenType("Almoço / Janta");
        restaurant.setLocation(savedLocation);
        restaurant.setRestaurantOwner(savedUser);

        final Restaurant savedRestaurant = restaurantUseCase.created(restaurant);

        final Menu menu = new Menu();
        menu.setName("X-bacon");
        menu.setDescription("2 fatias de bacon" +//
                "1 hamburger (bovino) " +//
                "1 pão de hamburger de brioche " +//
                "2 colheres (sopa) de maionese " +//
                "2 fatias de queijo cheddar " +//
                "2 colheres (sopa) de ketchup");
        menu.setPrice(BigDecimal.valueOf(35.50));
        menu.setRestaurant(savedRestaurant);
        menu.setImage("C:/Users/OneDrive/Imagens");

        final Menu savedMenu = menuUseCase.created(menu);

        assertEquals("X-bacon", savedMenu.getName());
        assertEquals(BigDecimal.valueOf(35.50), savedMenu.getPrice());
        assertEquals("C:/Users/OneDrive/Imagens", savedMenu.getImage());

    }

    @DisplayName("Atualizar cardápio com sucesso")
    @Test
    void testUpdateMenu() {

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

        final Location location = new Location();
        location.setActive(true);
        location.setAddress("Travessa Raimundo Mesquita");
        location.setNumber("7865");
        location.setNeighborhood("Bahia Velha");
        location.setZipCode("69911-586");
        location.setCity("Rio Branco");
        location.setState("Acrê");

        final Location savedLocation = locationUseCase.save(location);

        final Restaurant restaurant = new Restaurant();
        restaurant.setActive(true);
        restaurant.setName("Restaurante empório Th");
        restaurant.setKitchenType("Almoço / Janta");
        restaurant.setLocation(savedLocation);
        restaurant.setRestaurantOwner(savedUser);

        final Restaurant savedRestaurant = restaurantUseCase.created(restaurant);

        final Menu menu = new Menu();
        menu.setName("X-bacon");
        menu.setDescription("2 fatias de bacon" +//
                "1 hamburger (bovino) " +//
                "1 pão de hamburger de brioche " +//
                "2 colheres (sopa) de maionese " +//
                "2 fatias de queijo cheddar " +//
                "2 colheres (sopa) de ketchup");
        menu.setPrice(BigDecimal.valueOf(35.50));
        menu.setRestaurant(savedRestaurant);
        menu.setImage("C:/Users/OneDrive/Imagens");

        final Menu savedMenu = menuUseCase.created(menu);

        savedMenu.setPrice(BigDecimal.valueOf(38.50));
        savedMenu.setImage("C:/Users/OneDrive/Imagens/Lanches");

        final Menu updateMenu = menuUseCase.update(savedMenu);

        assertEquals("X-bacon", updateMenu.getName());
        assertEquals(BigDecimal.valueOf(38.50), updateMenu.getPrice());
        assertEquals("C:/Users/OneDrive/Imagens/Lanches", updateMenu.getImage());

    }

    @DisplayName("Buscar menu por id")
    @Test
    void testGet() {

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

        final Location location = new Location();
        location.setActive(true);
        location.setAddress("Travessa Raimundo Mesquita");
        location.setNumber("7865");
        location.setNeighborhood("Bahia Velha");
        location.setZipCode("69911-586");
        location.setCity("Rio Branco");
        location.setState("Acrê");

        final Location savedLocation = locationUseCase.save(location);

        final Restaurant restaurant = new Restaurant();
        restaurant.setActive(true);
        restaurant.setName("Restaurante empório Th");
        restaurant.setKitchenType("Almoço / Janta");
        restaurant.setLocation(savedLocation);
        restaurant.setRestaurantOwner(savedUser);

        final Restaurant savedRestaurant = restaurantUseCase.created(restaurant);

        final Menu newMenu = new Menu();
        newMenu.setName("X-bacon");
        newMenu.setDescription("2 fatias de bacon" +//
                "1 hamburger (bovino) " +//
                "1 pão de hamburger de brioche " +//
                "2 colheres (sopa) de maionese " +//
                "2 fatias de queijo cheddar " +//
                "2 colheres (sopa) de ketchup");
        newMenu.setPrice(BigDecimal.valueOf(35.50));
        newMenu.setRestaurant(savedRestaurant);
        newMenu.setImage("C:/Users/OneDrive/Imagens");

        final Menu savedMenu = menuUseCase.created(newMenu);

        final Long id = savedMenu.getId();

        final Menu menu = menuUseCase.findById(id).orElseThrow(() -> new IllegalStateException("Menu not found"));

        assertNotNull(menu);
        assertEquals(id, menu.getId());
        assertEquals("X-bacon", menu.getName());
        assertEquals(BigDecimal.valueOf(35.50), menu.getPrice());
        assertEquals("C:/Users/OneDrive/Imagens", menu.getImage());


    }

    @DisplayName("Buscar menu pela paginação")
    @Test
    void testGetAll() {

        final Role restaurantOwner = new Role();
        restaurantOwner.setName("Dono de restaurante");
        restaurantOwner.setType(RoleType.RESTAURANT_OWNER);

        final Role savedRestaurantOwner = roleUseCase.created(restaurantOwner);

        final User user = new User();
        user.setUsername("Thiago Depizzol");
        user.setLogin("thiago.depizzol@fiap.com.br");
        user.setPassword("12345678");
        user.setRole(savedRestaurantOwner);

        final User savedUser = userUseCase.created(user);

        final Location location = new Location();
        location.setActive(true);
        location.setAddress("Travessa Raimundo Mesquita");
        location.setNumber("7865");
        location.setNeighborhood("Bahia Velha");
        location.setZipCode("69911-586");
        location.setCity("Rio Branco");
        location.setState("Acrê");

        final Location savedLocation = locationUseCase.save(location);

        final Restaurant restaurant = new Restaurant();
        restaurant.setActive(true);
        restaurant.setName("Restaurante empório Th");
        restaurant.setKitchenType("Almoço / Janta");
        restaurant.setLocation(savedLocation);
        restaurant.setRestaurantOwner(savedUser);

        final Restaurant savedRestaurant = restaurantUseCase.created(restaurant);

        final Menu firtsMenu = new Menu();
        firtsMenu.setName("X-bacon");
        firtsMenu.setDescription("2 fatias de bacon" +//
                "1 hamburger (bovino) " +//
                "1 pão de hamburger de brioche " +//
                "maionese " +//
                "2 fatias de queijo cheddar " +//
                "ketchup");
        firtsMenu.setPrice(BigDecimal.valueOf(35.50));
        firtsMenu.setRestaurant(savedRestaurant);
        firtsMenu.setImage("C:/Users/OneDrive/Imagens");

        final Menu secondMenu = new Menu();
        secondMenu.setName("X-salada");
        secondMenu.setDescription("1 hamburger (bovino) " +//
                "1 cebola picada " +//
                "1 mostarda " +//
                "2 ketchup " +//
                "1 ovo");
        secondMenu.setPrice(BigDecimal.valueOf(32.00));
        secondMenu.setRestaurant(savedRestaurant);
        secondMenu.setImage("C:/Users/OneDrive/Imagens");

        final Menu thirdMenu = new Menu();
        thirdMenu.setName("X-egg");
        thirdMenu.setDescription("1 hamburger (bovino) " +
                "2 ovos " +
                "1 fatia de queijo " +
                "Alface " +
                "Tomate ");
        thirdMenu.setPrice(BigDecimal.valueOf(32.00));
        thirdMenu.setRestaurant(savedRestaurant);
        thirdMenu.setImage("C:/Users/OneDrive/Imagens");

        menuUseCase.created(firtsMenu);
        menuUseCase.created(secondMenu);
        menuUseCase.created(thirdMenu);

        final String username = "thiago.depizzol@fiap.com.br";
        final String password = "12345678";

        String credentials = username + ":" + password;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        String header = "Basic " + base64Credentials;

        final int page = 0;
        final int size = 10;

        final List<Menu> menus = menuUseCase.findAllActive(page, size, header);

        assertEquals(3, menus.size());
        assertTrue(menus.stream().anyMatch(menu -> menu.getName().equals("X-bacon")));
        assertTrue(menus.stream().anyMatch(menu -> menu.getName().equals("X-salada")));
        assertTrue(menus.stream().anyMatch(menu -> menu.getName().equals("X-egg")));

    }

    @DisplayName("Buscar cardápios pelo id do restaurante")
    @Test
    void testGetAllByRestaurant() {

        final Role restaurantOwner = new Role();
        restaurantOwner.setName("Dono de restaurante");
        restaurantOwner.setType(RoleType.RESTAURANT_OWNER);

        final Role savedRestaurantOwner = roleUseCase.created(restaurantOwner);

        final User user = new User();
        user.setUsername("Thiago Depizzol");
        user.setLogin("thiago.depizzol@fiap.com.br");
        user.setPassword("12345678");
        user.setRole(savedRestaurantOwner);

        final User savedUser = userUseCase.created(user);

        final Location location = new Location();
        location.setActive(true);
        location.setAddress("Travessa Raimundo Mesquita");
        location.setNumber("7865");
        location.setNeighborhood("Bahia Velha");
        location.setZipCode("69911-586");
        location.setCity("Rio Branco");
        location.setState("Acrê");

        final Location savedLocation = locationUseCase.save(location);

        final Restaurant restaurant = new Restaurant();
        restaurant.setActive(true);
        restaurant.setName("Restaurante empório Th");
        restaurant.setKitchenType("Almoço / Janta");
        restaurant.setLocation(savedLocation);
        restaurant.setRestaurantOwner(savedUser);

        final Restaurant savedRestaurant = restaurantUseCase.created(restaurant);

        final Long restaurantId = savedRestaurant.getId();

        final Menu firtsMenu = new Menu();
        firtsMenu.setName("X-bacon");
        firtsMenu.setDescription("2 fatias de bacon" +//
                "1 hamburger (bovino) " +//
                "1 pão de hamburger de brioche " +//
                "maionese " +//
                "2 fatias de queijo cheddar " +//
                "ketchup");
        firtsMenu.setPrice(BigDecimal.valueOf(35.50));
        firtsMenu.setRestaurant(savedRestaurant);
        firtsMenu.setImage("C:/Users/OneDrive/Imagens");

        final Menu secondMenu = new Menu();
        secondMenu.setName("X-salada");
        secondMenu.setDescription("1 hamburger (bovino) " +//
                "1 cebola picada " +//
                "1 mostarda " +//
                "2 ketchup " +//
                "1 ovo");
        secondMenu.setPrice(BigDecimal.valueOf(32.00));
        secondMenu.setRestaurant(savedRestaurant);
        secondMenu.setImage("C:/Users/OneDrive/Imagens");

        final Menu thirdMenu = new Menu();
        thirdMenu.setName("X-egg");
        thirdMenu.setDescription("1 hamburger (bovino) " +
                "2 ovos " +
                "1 fatia de queijo " +
                "Alface " +
                "Tomate ");
        thirdMenu.setPrice(BigDecimal.valueOf(32.00));
        thirdMenu.setRestaurant(savedRestaurant);
        thirdMenu.setImage("C:/Users/OneDrive/Imagens");

        menuUseCase.created(firtsMenu);
        menuUseCase.created(secondMenu);
        menuUseCase.created(thirdMenu);

        final List<Menu> menus = menuUseCase.getAllByRestaurant(restaurantId);

        assertEquals(3, menus.size());
        assertTrue(menus.stream().anyMatch(menu -> menu.getName().equals("X-bacon")));
        assertTrue(menus.stream().anyMatch(menu -> menu.getName().equals("X-salada")));
        assertTrue(menus.stream().anyMatch(menu -> menu.getName().equals("X-egg")));

    }


}
