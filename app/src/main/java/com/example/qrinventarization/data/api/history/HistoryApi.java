package com.example.qrinventarization.data.api.history;

import com.example.qrinventarization.domain.model.history.Histories;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface HistoryApi {
    @GET("history/{id}")
    Call<Histories> getHistory(@Path("id") long id);
}
