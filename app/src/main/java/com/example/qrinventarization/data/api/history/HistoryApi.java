package com.example.qrinventarization.data.api.history;

import com.example.qrinventarization.domain.model.history.Histories;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface HistoryApi {
    @GET("history")
    Call<Histories> getHistory();
}
