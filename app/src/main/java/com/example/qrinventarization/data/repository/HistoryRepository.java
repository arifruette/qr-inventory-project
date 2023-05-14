package com.example.qrinventarization.data.repository;

import com.example.qrinventarization.data.api.history.HistoryApi;
import com.example.qrinventarization.data.api.history.HistoryApiService;
import com.example.qrinventarization.domain.model.history.Histories;

import retrofit2.Call;

public class HistoryRepository {
    public static Call<Histories> getHistory(long id){return HistoryApiService.getInstance().getHistory(id);}

}
