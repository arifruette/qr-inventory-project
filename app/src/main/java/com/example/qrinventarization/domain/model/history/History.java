package com.example.qrinventarization.domain.model.history;

public class History {
    private String date;
    private long id;
    private String new_place;
    private long obj_id;
    private String old_place;

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

    public String getNew_place() {
        return new_place;
    }
    public String getOld_place(){
        return old_place;
    }


}
