package com.example.qrinventarization.feature.object.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qrinventarization.data.repository.ItemsRepository;
import com.example.qrinventarization.data.repository.PlacesRepository;
import com.example.qrinventarization.domain.model.items.Item;
import com.example.qrinventarization.domain.model.items.Object;
import com.example.qrinventarization.domain.model.places.Place;
import com.example.qrinventarization.domain.model.places.Places;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObjectViewModel extends ViewModel {

    private MutableLiveData<Item> _item = new MutableLiveData<>();
    public LiveData<Item> item = _item;

    private MutableLiveData<ObjectStatus> _status = new MutableLiveData<>();
    public LiveData<ObjectStatus> status = _status;
    private MutableLiveData<List<Place>> _places = new MutableLiveData<>();
    public LiveData<List<Place>> places = _places;

    public void load(long id){
        _status.setValue(ObjectStatus.LOADING);
        ItemsRepository.getItem(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                _status.setValue(ObjectStatus.LOADED);
                _item.setValue((Item) response.body().objects);
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                _status.setValue(ObjectStatus.FAILURE);
                t.printStackTrace();
            }
        });
    }

    public void delete(long id){

        ItemsRepository.deleteItem(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                _status.setValue(ObjectStatus.DELETED);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                _status.setValue(ObjectStatus.FAILURE);
                t.printStackTrace();
            }
        });
    }

    public void update(long id){
        //TODO
    }

    public void history(long id){
        //TODO
    }

    public void places(){
        _status.setValue(ObjectStatus.LOADING_PLACES);
        PlacesRepository.getPlaces().enqueue(new Callback<Places>() {
            @Override
            public void onResponse(@NonNull Call<Places> call, @NonNull Response<Places> response) {
                _status.setValue(ObjectStatus.LOADED_PLACES);
                _places.setValue(response.body().places);
            }

            @Override
            public void onFailure(@NonNull Call<Places> call, @NonNull Throwable t) {
                _status.setValue(ObjectStatus.FAILURE_PLACES);
                t.printStackTrace();
            }
        });
    }

}
