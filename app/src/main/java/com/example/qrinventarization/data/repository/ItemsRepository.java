package com.example.qrinventarization.data.repository;


import com.example.qrinventarization.data.api.items.ItemApi;
import com.example.qrinventarization.data.api.items.ItemApiService;
import com.example.qrinventarization.domain.model.items.HasImage;
import com.example.qrinventarization.domain.model.items.Item;
import com.example.qrinventarization.domain.model.items.Items;
import com.example.qrinventarization.domain.model.items.Object;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ItemsRepository {
    public static Call<Items> getItems(String token){
        return ItemApiService.getInstance().getItems("Bearer " + token);
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
    public static Call<ResponseBody> getImage(long id){
        return ItemApiService.getInstance().getImage(id);
    }
    public static Call<HasImage> hasImage(long id){return ItemApiService.getInstance().hasImage(id);
    }
 }
