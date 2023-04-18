package com.example.qrinventarization.data.repository;

import com.example.qrinventarization.data.api.items.ItemApi;
import com.example.qrinventarization.data.api.items.ItemApiService;
import com.example.qrinventarization.domain.model.Item;
import com.example.qrinventarization.domain.model.Items;
import com.example.qrinventarization.domain.model.Object;

import java.util.List;

import retrofit2.Call;

public class ItemsRepository {
    public static Call<Items> getItems(){
        return ItemApiService.getInstance().getItems();
    }
    public static Call<Object> getItem(long id){
        return ItemApiService.getInstance().getItem(id);
    }
    public static Call<Void> deleteItem(long id){
        return ItemApiService.getInstance().deleteUser(id);
    }
 }