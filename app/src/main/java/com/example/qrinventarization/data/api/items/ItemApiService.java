package com.example.qrinventarization.data.api.items;

import com.example.qrinventarization.data.api.RetrofitService;

public class ItemApiService {

    private static ItemApi itemApi;

    private static ItemApi create(){
        return RetrofitService.getInstance().create(ItemApi.class);
    }

    public static ItemApi getInstance(){
        if(itemApi == null) itemApi = create();
        return itemApi;
    }
}
