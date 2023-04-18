package com.example.qrinventarization.data.repository;

import com.example.qrinventarization.data.api.places.PlacesApiService;
import com.example.qrinventarization.domain.model.places.Places;

import retrofit2.Call;

public class PlacesRepository {
    public static Call<Places> getPlaces()
    {
        return PlacesApiService.getInstance().getPlaces();
    }
}
