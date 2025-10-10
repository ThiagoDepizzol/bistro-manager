package com.app.infra.gateway.validation.menu;

import com.app.core.domain.enums.RoleType;
import com.app.core.domain.restaurant.Menu;
import com.app.core.domain.restaurant.Restaurant;
import com.app.core.exception.DomainException;
import com.app.core.gateways.validation.menu.MenuAuthorizationGateway;
import com.app.infra.application.dto.authentication.LoginDTO;
import com.app.infra.entity.restaurant.RestaurantEntity;
import com.app.infra.entity.user.UserEntity;
import com.app.infra.repository.restaurant.RestaurantRepository;
import com.app.infra.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;

public class MenuAuthorizationRepositoryGateway implements MenuAuthorizationGateway {

    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;

    public MenuAuthorizationRepositoryGateway(final UserRepository userRepository, final RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void userHasPermissionToCreated(LoginDTO dto, List<RoleType> types, Menu menu) {

        final Long restaurantId = Optional.ofNullable(menu.getRestaurant())
                .map(Restaurant::getId)
                .orElseThrow(() -> new DomainException("Restaurante não encontrado"));

        final UserEntity userEntity = userRepository.findByEmail(dto.getLogin())
                .orElseThrow(() -> new DomainException("Usuário não encontrado"));

        final RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new DomainException("Restaurante não encontrado"));

        final Long userId = Optional.ofNullable(restaurantEntity.getRestaurantOwner())
                .map(UserEntity::getId)
                .orElseThrow(() -> new DomainException("Dono de restaurante não encontrado"));

        final List<String> strings = types.stream()
                .map(Enum::toString)
                .toList();

        final boolean isUserOwnerRestaurant = userId.equals(userEntity.getId());

        if (!isUserOwnerRestaurant) {

            throw new DomainException("Usuário apresentado não é dono do restaurante");

        }

        final boolean userHasRestaurantOwnerRole = userRepository.userHasPermission(userEntity.getId(), strings);

        if (!userHasRestaurantOwnerRole) {

            throw new DomainException("Usuário não tem permissão para criar/atualizar item do cardápio");
        }

    }

}
