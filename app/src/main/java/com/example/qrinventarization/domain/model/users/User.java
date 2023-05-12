package com.example.qrinventarization.domain.model.users;

public class User {
    private final String login;
    private final String password;

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }
}
