package com.app.infra.entity.user;

import com.app.infra.entity.base.BaseEntity;
import com.app.infra.entity.roles.RoleEntity;
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

    @JoinColumn(name = "adm_role_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RoleEntity role;

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

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
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

    public UserEntity(Long id, String username, String login, String password, RoleEntity role) {
        this.id = id;
        this.username = username;
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
