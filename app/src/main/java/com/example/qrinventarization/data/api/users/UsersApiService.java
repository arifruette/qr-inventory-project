package com.example.qrinventarization.data.api.users;

import com.example.qrinventarization.data.api.RetrofitService;
import com.example.qrinventarization.data.api.places.PlacesApi;

public class UsersApiService {
    private static UsersApi usersApi;

    private static UsersApi create(){
        return RetrofitService.getInstance().create(UsersApi.class);
    }

    public static UsersApi getInstance(){
        if(usersApi == null) usersApi = create();
        return usersApi;
    }
}
