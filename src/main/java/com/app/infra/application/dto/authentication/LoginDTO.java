package com.app.infra.application.dto.authentication;

public class LoginDTO {

    private String login;

    private String password;

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

    public LoginDTO() {
    }

    public LoginDTO(final String login, final String password) {
        this.login = login;
        this.password = password;
    }
}
