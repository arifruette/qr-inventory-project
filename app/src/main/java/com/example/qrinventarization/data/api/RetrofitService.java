package com.example.qrinventarization.data.api;

import java.io.IOException;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;


public class RetrofitService {


    private static Retrofit retrofit;

    private static Retrofit create(){
        String BASE_URL = "http://10.18.1.134:8080/api/";

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static Retrofit getInstance(){
        if(retrofit == null) retrofit = create();
        return retrofit;
    }
}
