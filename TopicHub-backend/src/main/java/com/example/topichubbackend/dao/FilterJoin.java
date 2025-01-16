package com.example.topichubbackend.dao;

public enum FilterJoin {

    DEFAULT("DEFAULT"),
    SUBSCRIPTION ("SUBSCRIPTION"),
    HUB ("HUB");

    private String title;

    FilterJoin(String title) {
        this.title = title;
    }

    public String value() {
        return title;
    }
}
