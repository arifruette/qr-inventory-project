package com.example.qrinventarization.domain.model.users;

public class Token {
    private String access_token;
    private Boolean is_admin;

    public String getAccess_token() {
        return this.access_token;
    }


    public Boolean isIs_admin() {
        return this.is_admin;
    }
}
