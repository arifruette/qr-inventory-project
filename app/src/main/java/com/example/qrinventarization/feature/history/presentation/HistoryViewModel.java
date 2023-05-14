package com.example.qrinventarization.feature.history.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qrinventarization.data.repository.HistoryRepository;
import com.example.qrinventarization.domain.model.history.Histories;
import com.example.qrinventarization.domain.model.history.History;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryViewModel extends ViewModel {


    private MutableLiveData<HistoryStatus> _status = new MutableLiveData<>();
    public LiveData<HistoryStatus> status = _status;

    private MutableLiveData<List<History>> _histories = new MutableLiveData<>();
    public LiveData<List<History>> histories = _histories;




    public void history(long id){
        _status.setValue(HistoryStatus.LOADING);
        HistoryRepository.getHistory(id).enqueue(new Callback<Histories>() {
            @Override
            public void onResponse(@NonNull Call<Histories> call, @NonNull Response<Histories> response) {
                _status.setValue(HistoryStatus.LOADED);
                _histories.setValue(response.body().history);

            }

            @Override
            public void onFailure(@NonNull Call<Histories> call, @NonNull Throwable t) {
                t.printStackTrace();
                _status.setValue(HistoryStatus.FAILURE);
            }
        });
    }
}
