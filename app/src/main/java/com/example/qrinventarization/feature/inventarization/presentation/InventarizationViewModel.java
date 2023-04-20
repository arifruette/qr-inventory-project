package com.example.qrinventarization.feature.inventarization.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qrinventarization.data.repository.ItemsRepository;
import com.example.qrinventarization.domain.model.items.Item;
import com.example.qrinventarization.domain.model.items.Items;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventarizationViewModel extends ViewModel {

    private MutableLiveData<InventarizationStatus> _status = new MutableLiveData<>();
    private LiveData<InventarizationStatus> status = _status;

    private MutableLiveData<List<Item>> _items = new MutableLiveData<>();
    private LiveData<List<Item>> items = _items;

    public void load(String places){
        _status.setValue(InventarizationStatus.LOADING);
        ItemsRepository.getItems().enqueue(new Callback<Items>() {
            @Override
            public void onResponse(@NonNull Call<Items> call, @NonNull Response<Items> response) {
                _status.setValue(InventarizationStatus.LOADED);

            }

            @Override
            public void onFailure(@NonNull Call<Items> call, @NonNull Throwable t) {
                _status.setValue(InventarizationStatus.FAILURE);
                t.printStackTrace();
            }
        });
    }
}

