package com.example.qrinventarization.domain.model.places;

public class Place {
    private long id;
    private Object text;
    private boolean checked;

    public long getId() {
        return id;
    }

    public Object getText() {
        return text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
