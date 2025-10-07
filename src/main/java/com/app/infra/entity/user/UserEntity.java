package com.app.infra.entity.user;

import com.app.infra.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usr_users")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @Column(unique = true)
    private UUID loginHash;

    private Instant loginDate;

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

    public UUID getLoginHash() {
        return loginHash;
    }

    public void setLoginHash(UUID loginHash) {
        this.loginHash = loginHash;
    }

    public Instant getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Instant loginDate) {
        this.loginDate = loginDate;
    }

    public UserEntity() {
    }

    public UserEntity(String username, String login, String password) {
        this.username = username;
        this.login = login;
        this.password = password;
    }
}
