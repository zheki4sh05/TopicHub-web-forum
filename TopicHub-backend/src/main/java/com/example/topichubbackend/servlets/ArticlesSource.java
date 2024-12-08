package com.example.topichubbackend.servlets;

public enum ArticlesSource {
    OWN ("OWN"),
    OTHER ("OTHER"),
    MARKS("MARKS");

    private String type;

    ArticlesSource(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
