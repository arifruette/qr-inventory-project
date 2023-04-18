package com.example.qrinventarization.feature.object.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qrinventarization.data.repository.ItemsRepository;
import com.example.qrinventarization.domain.model.Item;
import com.example.qrinventarization.domain.model.Items;
import com.example.qrinventarization.domain.model.Object;
import com.example.qrinventarization.feature.items.presentation.ItemsStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObjectViewModel extends ViewModel {

    private MutableLiveData<Item> _item = new MutableLiveData<>();
    public LiveData<Item> item = _item;

    private MutableLiveData<ObjectStatus> _status = new MutableLiveData<>();
    public LiveData<ObjectStatus> status = _status;

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

}
