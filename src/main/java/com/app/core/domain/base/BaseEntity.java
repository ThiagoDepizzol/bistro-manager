package com.app.core.domain.base;

public abstract class BaseEntity {

    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BaseEntity() {
    }
}
