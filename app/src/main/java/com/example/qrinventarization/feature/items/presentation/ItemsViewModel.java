package com.example.qrinventarization.feature.items.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qrinventarization.data.repository.ItemsRepository;
import com.example.qrinventarization.domain.model.Item;
import com.example.qrinventarization.domain.model.Items;
import com.example.qrinventarization.feature.items.ui.ItemsFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsViewModel extends ViewModel {

    private MutableLiveData<List<Item>> _items = new MutableLiveData<>();
    public LiveData<List<Item>> items = _items;

    private MutableLiveData<ItemsStatus> _status = new MutableLiveData<>();
    public LiveData<ItemsStatus> status = _status;


    public void load(){
        _status.setValue(ItemsStatus.LOADING);
        ItemsRepository.getItems().enqueue(new Callback<Items>() {
            @Override
            public void onResponse(@NonNull Call<Items> call, @NonNull Response<Items> response) {
                _status.setValue(ItemsStatus.LOADED);
                _items.setValue(response.body().objects);
            }

            @Override
            public void onFailure(@NonNull Call<Items> call, @NonNull Throwable t) {
                _status.setValue(ItemsStatus.FAILURE);
                t.printStackTrace();

            }
        });
    }
}
