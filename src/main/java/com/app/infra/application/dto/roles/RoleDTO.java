package com.app.infra.application.dto.roles;

import com.app.core.domain.enums.RoleType;

public class RoleDTO {

    private Long id;

    private String name;

    private RoleType type;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoleDTO() {
    }

    public RoleDTO(Long id, String name, RoleType type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
    }
}
