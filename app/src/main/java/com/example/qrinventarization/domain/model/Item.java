package com.example.qrinventarization.domain.model;

import com.google.gson.annotations.SerializedName;

public class Item {

    private long id;

    private String name;

    private String serial_number;

    private String place;

    public Item(long id, String name, String serial_number, String place) {
        this.id = id;
        this.name = name;
        this.serial_number = serial_number;
        this.place = place;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public String getPlace() {
        return place;
    }
}
