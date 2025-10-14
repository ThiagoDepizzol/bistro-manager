package com.app.domain.entity.restaurant;

import com.app.core.domain.enums.RoleType;
import com.app.core.domain.location.Location;
import com.app.core.domain.restaurant.Restaurant;
import com.app.core.domain.role.Role;
import com.app.core.domain.user.User;
import com.app.core.gateways.location.LocationGateway;
import com.app.core.gateways.restaurant.RestaurantGateway;
import com.app.core.gateways.roles.RoleGateway;
import com.app.core.gateways.user.UserGateway;
import com.app.core.usecases.location.LocationUseCase;
import com.app.core.usecases.restaurant.RestaurantUseCase;
import com.app.core.usecases.roles.RoleUseCase;
import com.app.core.usecases.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {

    private RestaurantUseCase restaurantUseCase;

    private RoleUseCase roleUseCase;

    private UserUseCase userUseCase;

    private LocationUseCase locationUseCase;

    @BeforeEach
    void setUp() {

        final RestaurantGateway restaurantGateway = Mockito.mock(RestaurantGateway.class);

        final RoleGateway roleGateway = Mockito.mock(RoleGateway.class);

        final UserGateway userGateway = Mockito.mock(UserGateway.class);

        final LocationGateway locationGateway = Mockito.mock(LocationGateway.class);

        final Map<Long, Restaurant> restaurantMap = new HashMap<>();

        final AtomicLong counter = new AtomicLong(1L);

        Mockito.when(restaurantGateway.created(Mockito.any(Restaurant.class)))
                .thenAnswer(invocation -> {

                    final Restaurant restaurant = invocation.getArgument(0);

                    if (restaurant.getId() == null) {
                        restaurant.setId(counter.getAndIncrement());
                    }

                    restaurantMap.put(restaurant.getId(), restaurant);
                    return restaurant;
                });

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

        Mockito.when(locationGateway.save(Mockito.any(Location.class)))
                .thenAnswer(invocation -> {

                    final Location location = invocation.getArgument(0);

                    if (location.getId() == null) {
                        location.setId(counter.getAndIncrement());
                    }

                    return location;
                });


        Mockito.when(restaurantGateway.update(Mockito.any(Restaurant.class)))
                .thenAnswer(invocation -> {

                    final Restaurant restaurant = invocation.getArgument(0);

                    if (restaurant.getId() == null) {
                        restaurant.setId(counter.getAndIncrement());
                    }

                    restaurantMap.put(restaurant.getId(), restaurant);
                    return restaurant;
                });

        Mockito.when(restaurantGateway.findById(Mockito.anyLong()))
                .thenAnswer(invocation -> {

                    final Long id = invocation.getArgument(0);

                    return Optional.ofNullable(restaurantMap.get(id));
                });

        Mockito.when(restaurantGateway.findAllActive(Mockito.anyInt(), Mockito.anyInt()))
                .thenAnswer(invocation -> {

                    final int page = invocation.getArgument(0);
                    final int size = invocation.getArgument(1);

                    final List<Restaurant> activeRoles = new ArrayList<>(restaurantMap.values());

                    final int fromIndex = page * size;
                    final int toIndex = Math.min(fromIndex + size, activeRoles.size());

                    if (fromIndex >= activeRoles.size()) {
                        return Collections.emptyList();
                    }

                    return activeRoles.subList(fromIndex, toIndex);

                });

        restaurantUseCase = new RestaurantUseCase(restaurantGateway);
        locationUseCase = new LocationUseCase(locationGateway);
        userUseCase = new UserUseCase(userGateway);
        roleUseCase = new RoleUseCase(roleGateway);
    }

    @DisplayName("Criar restaurante com sucesso")
    @Test
    void testCreateRestaurant() {

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

        assertEquals("Restaurante empório Th", savedRestaurant.getName());
        assertEquals("Almoço / Janta", savedRestaurant.getKitchenType());

    }

    @DisplayName("Atualizar restaurante com sucesso")
    @Test
    void testUpdateRestaurant() {

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

        savedRestaurant.setName("Pizzaria a lenha MP");
        savedRestaurant.setKitchenType("Janta");

        final Restaurant updateRestaurant = restaurantUseCase.update(savedRestaurant);

        assertEquals("Pizzaria a lenha MP", updateRestaurant.getName());
        assertEquals("Janta", updateRestaurant.getKitchenType());


    }

    @DisplayName("Buscar restaurante por id")
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

        final Restaurant newRestaurant = new Restaurant();
        newRestaurant.setActive(true);
        newRestaurant.setName("Restaurante empório Th");
        newRestaurant.setKitchenType("Almoço / Janta");
        newRestaurant.setLocation(savedLocation);
        newRestaurant.setRestaurantOwner(savedUser);

        final Restaurant savedRestaurant = restaurantUseCase.created(newRestaurant);

        final Long id = savedRestaurant.getId();

        final Restaurant restaurant = restaurantUseCase.findById(id)
                .orElseThrow(() -> new IllegalStateException("Restaurant not found"));

        assertNotNull(restaurant);
        assertEquals(id, restaurant.getId());
        assertEquals("Restaurante empório Th", restaurant.getName());

    }

    @DisplayName("Buscar restaurante pela paginação")
    @Test
    void testGetAll() {

        final Role restaurantOwner = new Role();
        restaurantOwner.setName("Dono de restaurante");
        restaurantOwner.setType(RoleType.RESTAURANT_OWNER);

        final Role savedRestaurantOwner = roleUseCase.created(restaurantOwner);

        final User firstUser = new User();
        firstUser.setUsername("Thiago Depizzol");
        firstUser.setLogin("thiago.depizzol@fiap.com.br");
        firstUser.setPassword("12345678");
        firstUser.setRole(savedRestaurantOwner);

        final User secondUser = new User();
        secondUser.setUsername("Maria Silva");
        secondUser.setLogin("maria.silva@fiap.com.br");
        secondUser.setPassword("87654321");
        secondUser.setRole(restaurantOwner);

        final User thirdUser = new User();
        thirdUser.setUsername("João Souza");
        thirdUser.setLogin("joao.souza@fiap.com.br");
        thirdUser.setPassword("abcdef12");
        thirdUser.setRole(restaurantOwner);

        final User savedFirstUser = userUseCase.created(firstUser);
        final User savedSecondUser = userUseCase.created(secondUser);
        final User savedThirdUser = userUseCase.created(thirdUser);

        final Location firtsLocation = new Location();
        firtsLocation.setActive(true);
        firtsLocation.setAddress("Travessa Raimundo Mesquita");
        firtsLocation.setNumber("7865");
        firtsLocation.setNeighborhood("Bahia Velha");
        firtsLocation.setZipCode("69911-586");
        firtsLocation.setCity("Rio Branco");
        firtsLocation.setState("Acrê");

        final Location secondLocation = new Location();
        secondLocation.setActive(true);
        secondLocation.setAddress("Rua Ágata Verde");
        secondLocation.setNumber("12236");
        secondLocation.setNeighborhood("Lago Azul");
        secondLocation.setZipCode("69018-580");
        secondLocation.setCity("Manaus");
        secondLocation.setState("Amazonas");

        final Location thirdLocation = new Location();
        thirdLocation.setActive(true);
        thirdLocation.setAddress("Travessa Aruba");
        thirdLocation.setNumber("8563");
        thirdLocation.setNeighborhood("Bangu");
        thirdLocation.setZipCode("21840-426");
        thirdLocation.setCity("Rio de Janeiro");
        thirdLocation.setState("Rio de Janeiro");

        final Location savedFirstLocation = locationUseCase.save(firtsLocation);
        final Location savedSecondLocation = locationUseCase.save(secondLocation);
        final Location savedThirdLocation = locationUseCase.save(thirdLocation);

        final Restaurant firstRestaurant = new Restaurant();
        firstRestaurant.setActive(true);
        firstRestaurant.setName("Restaurante empório Th");
        firstRestaurant.setKitchenType("Almoço / Janta");
        firstRestaurant.setLocation(savedFirstLocation);
        firstRestaurant.setRestaurantOwner(savedFirstUser);

        final Restaurant secondRestaurant = new Restaurant();
        secondRestaurant.setActive(true);
        secondRestaurant.setName("Lanchonete solaris");
        secondRestaurant.setKitchenType("Janta");
        secondRestaurant.setLocation(savedSecondLocation);
        secondRestaurant.setRestaurantOwner(savedSecondUser);

        final Restaurant thirdRestaurant = new Restaurant();
        thirdRestaurant.setActive(true);
        thirdRestaurant.setName("Padaria doce sonho");
        thirdRestaurant.setKitchenType("Café da manhã");
        thirdRestaurant.setLocation(savedThirdLocation);
        thirdRestaurant.setRestaurantOwner(savedThirdUser);

        restaurantUseCase.created(firstRestaurant);
        restaurantUseCase.created(secondRestaurant);
        restaurantUseCase.created(thirdRestaurant);

        final int page = 0;
        final int size = 10;

        final List<Restaurant> restaurants = restaurantUseCase.findAllActive(page, size);

        assertEquals(3, restaurants.size());
        assertTrue(restaurants.stream().anyMatch(restaurant -> restaurant.getName().equals("Restaurante empório Th")));
        assertTrue(restaurants.stream().anyMatch(restaurant -> restaurant.getName().equals("Lanchonete solaris")));
        assertTrue(restaurants.stream().anyMatch(restaurant -> restaurant.getName().equals("Padaria doce sonho")));

    }


}
