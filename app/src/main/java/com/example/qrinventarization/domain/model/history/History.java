package com.example.qrinventarization.domain.model.history;

public class History {
    private String date;
    private long id;
    private long new_place_id;
    private long obj_id;
    private long obj_place_id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNew_place_id() {
        return new_place_id;
    }

    public void setNew_place_id(long new_place_id) {
        this.new_place_id = new_place_id;
    }

    public long getObj_id() {
        return obj_id;
    }

    public void setObj_id(long obj_id) {
        this.obj_id = obj_id;
    }

    public long getObj_place_id() {
        return obj_place_id;
    }

    public void setObj_place_id(long obj_place_id) {
        this.obj_place_id = obj_place_id;
    }
}
