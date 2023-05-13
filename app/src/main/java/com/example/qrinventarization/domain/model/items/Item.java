package com.example.qrinventarization.domain.model.items;

import com.google.gson.annotations.SerializedName;

public class Item {

    private long id;

    private String name;

    private String serial_number;

    private String obj_place_text;
    private String obj_place;
    private int checked;

    public Item(long id, String name, String serial_number, String obj_place) {
        this.id = id;
        this.name = name;
        this.serial_number = serial_number;
        this.obj_place = obj_place;
    }

    public Item(String name, String serial_number, String obj_place){
        this.name = name;
        this.serial_number = serial_number;
        this.obj_place = obj_place;
    }

    public Item(String name, String serial_number){
        this.name = name;
        this.serial_number = serial_number;
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
        return obj_place_text;
    }

    public int getChecked(){
        return checked;
    }

    public void setCheckedItem(int checked) {
        this.checked = checked;
    }

    public String getObj_place() {
        return obj_place;
    }

    public void setObj_place(String obj_place) {
        this.obj_place = obj_place;
    }
}
