package com.example.qrinventarization.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Items {

    public List<Item> objects;
    public Items(List<Item> obj){
        this.objects = obj;
    }
}
