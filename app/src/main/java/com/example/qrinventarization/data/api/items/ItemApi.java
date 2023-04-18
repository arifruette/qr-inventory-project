package com.example.qrinventarization.data.api.items;

import com.example.qrinventarization.domain.model.items.Item;
import com.example.qrinventarization.domain.model.items.Items;
import com.example.qrinventarization.domain.model.items.Object;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface ItemApi {

    @Headers(
            {
                    "Host: 192.168.0.109:8080",
                    "User-Agent: Mozilla"
            })
    @GET("objects")
    Call<Items> getItems();

    @GET("objects/{id}")
    Call<Object> getItem(
            @Path("id")
            long id
    );

    @DELETE("objects/{id}")
    Call<Void> deleteUser(
            @Path("id")
            long id
    );

    @PATCH("objects/{id}")
    Call<Void> updateObject(@Path("id") long id, @Body Item item);
}
