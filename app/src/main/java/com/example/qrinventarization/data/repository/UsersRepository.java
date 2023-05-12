package com.example.qrinventarization.data.repository;

import com.example.qrinventarization.data.api.users.UsersApiService;
import com.example.qrinventarization.domain.model.users.Token;
import com.example.qrinventarization.domain.model.users.User;

import retrofit2.Call;

public class UsersRepository {
    public static Call<Token> loginUser(User user) {return UsersApiService.getInstance().loginUser(user);}
}
