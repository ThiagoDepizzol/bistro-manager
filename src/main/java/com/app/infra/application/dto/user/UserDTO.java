package com.app.infra.application.dto.user;

import com.app.infra.application.dto.roles.RoleDTO;

public class UserDTO {
    private Long id;

    private String username;

    private RoleDTO role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public UserDTO() {
    }
}
