package com.app.infra.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public abstract class BaseEntity {

    @NotNull
    @Column(nullable = false)
    private Boolean active = true;

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BaseEntity() {
    }
}
