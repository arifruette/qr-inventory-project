package com.example.qrinventarization.data.api.places;

import com.example.qrinventarization.data.api.RetrofitService;
import com.example.qrinventarization.domain.model.places.Place;

public class PlacesApiService {

    private static PlacesApi placesApi;

    private static PlacesApi create(){
        return RetrofitService.getInstance().create(PlacesApi.class);
    }

    public static PlacesApi getInstance(){
        if(placesApi == null) placesApi = create();
        return placesApi;
    }
}