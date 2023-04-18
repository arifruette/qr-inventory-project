package com.example.qrinventarization.feature.places.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.qrinventarization.data.repository.PlacesRepository;
import com.example.qrinventarization.domain.model.places.Place;
import com.example.qrinventarization.domain.model.places.Places;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesViewModel extends ViewModel {
    private MutableLiveData<PlacesStatus> _status = new MutableLiveData<>();
    public LiveData<PlacesStatus> status = _status;

    private MutableLiveData<List<Place>> _places = new MutableLiveData<>();
    public LiveData<List<Place>> places = _places;

    public void load(){
        _status.setValue(PlacesStatus.LOADING);
        PlacesRepository.getPlaces().enqueue(new Callback<Places>() {
            @Override
            public void onResponse(@NonNull Call<Places> call, @NonNull Response<Places> response) {
                _status.setValue(PlacesStatus.LOADED);
                _places.setValue(response.body().places);
            }

            @Override
            public void onFailure(@NonNull Call<Places> call, @NonNull Throwable t) {
                t.printStackTrace();
                _status.setValue(PlacesStatus.FAILURE);
            }
        });
    }
}
