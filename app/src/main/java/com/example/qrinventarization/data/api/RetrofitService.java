package com.example.qrinventarization.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {
    private static String BASE_URL = "http://10.23.0.116:27016/api/";

    private static Retrofit retrofit;

    private static Retrofit create(){


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
