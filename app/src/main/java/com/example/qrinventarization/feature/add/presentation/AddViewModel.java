package com.example.qrinventarization.feature.add.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qrinventarization.data.repository.ItemsRepository;
import com.example.qrinventarization.data.repository.PlacesRepository;
import com.example.qrinventarization.domain.model.items.Item;
import com.example.qrinventarization.domain.model.items.Items;
import com.example.qrinventarization.domain.model.places.Place;
import com.example.qrinventarization.domain.model.places.Places;
import com.example.qrinventarization.feature.places.presentation.PlacesStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddViewModel extends ViewModel {

    private MutableLiveData<AddStatus> _status = new MutableLiveData<>();
    public LiveData<AddStatus> status = _status;

    private MutableLiveData<List<Place>> _places = new MutableLiveData<>();
    public LiveData<List<Place>> places = _places;

    public void add(Item item){
        _status.setValue(AddStatus.LOADING);
        ItemsRepository.addObject(item).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                _status.setValue(AddStatus.LOADED);

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                _status.setValue(AddStatus.FAILURE);
                t.printStackTrace();
            }
        });
    }

    public void get_places(){
        _status.setValue(AddStatus.LOADING);
        PlacesRepository.getPlaces().enqueue(new Callback<Places>() {
            @Override
            public void onResponse(@NonNull Call<Places> call, @NonNull Response<Places> response) {
                _status.setValue(AddStatus.LOADED);
                _places.setValue(response.body().places);
            }

            @Override
            public void onFailure(@NonNull Call<Places> call, @NonNull Throwable t) {
                t.printStackTrace();
                _status.setValue(AddStatus.FAILURE);
            }
        });
    }

}

