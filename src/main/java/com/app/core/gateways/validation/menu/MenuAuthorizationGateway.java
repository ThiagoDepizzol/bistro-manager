package com.app.core.gateways.validation.menu;

import com.app.core.domain.enums.RoleType;
import com.app.core.domain.restaurant.Menu;
import com.app.infra.application.dto.authentication.LoginDTO;

import java.util.List;

public interface MenuAuthorizationGateway {

    void userHasPermissionToCreated(LoginDTO dto, List<RoleType> types, Menu menu);

}
