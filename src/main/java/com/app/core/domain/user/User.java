package com.app.core.domain.user;

import com.app.core.domain.base.BaseEntity;
import com.app.core.domain.role.Role;

public class User extends BaseEntity {

    private Long id;

    private String username;

    private String login;

    private String password;

    private Role role;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User() {
    }

    public User(String username, String login, String password) {
        this.username = username;
        this.login = login;
        this.password = password;
    }

    public User(String username, String login, String password, Role role) {
        this.username = username;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(Long id, String username, String login, String password) {
        this.id = id;
        this.username = username;
        this.login = login;
        this.password = password;
    }

    public User(Long id, String username, String login, String password, Role role) {
        this.id = id;
        this.username = username;
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
