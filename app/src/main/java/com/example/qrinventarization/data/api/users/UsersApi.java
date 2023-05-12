package com.example.qrinventarization.data.api.users;

import com.example.qrinventarization.domain.model.users.Token;
import com.example.qrinventarization.domain.model.users.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsersApi {
    @POST("login")
    Call<Token> loginUser(@Body User user);
}
