package com.example.qrinventarization.data.api.history;

import com.example.qrinventarization.data.api.RetrofitService;
import com.example.qrinventarization.data.api.items.ItemApi;

public class HistoryApiService {
    private static HistoryApi historyApi;
    private static HistoryApi create(){
        return RetrofitService.getInstance().create(HistoryApi.class);
    }

    public static HistoryApi getInstance(){
        if(historyApi == null) historyApi = create();
        return historyApi;
    }
}
