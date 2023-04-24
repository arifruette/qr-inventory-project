package com.example.qrinventarization.data.repository;


import com.example.qrinventarization.data.api.items.ItemApiService;
import com.example.qrinventarization.domain.model.items.Item;
import com.example.qrinventarization.domain.model.items.Items;
import com.example.qrinventarization.domain.model.items.Object;

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
    public static Call<Void> updateObject(long id, Item item){
        return ItemApiService.getInstance().updateObject(id, item);
    }
    public static Call<Void> addObject(Item item){
        return ItemApiService.getInstance().addObject(item);
    }
 }
