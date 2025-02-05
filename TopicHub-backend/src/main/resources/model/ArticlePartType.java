package com.example.topichubbackend.model;

public enum ArticlePartType {
    PARAGRAPH ("paragraph"),
    LIST ("list"),
    CHAPTER ("chapter"),
    IMG ("img");

    private String type;

    ArticlePartType(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
