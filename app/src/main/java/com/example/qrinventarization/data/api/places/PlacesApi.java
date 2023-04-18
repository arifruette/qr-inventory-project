package com.example.qrinventarization.data.api.places;

import com.example.qrinventarization.domain.model.places.Places;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlacesApi {
    @GET("places")
    Call<Places> getPlaces();
}
