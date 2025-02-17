package com.example.topichubbackend.dto;

public enum StatusDto {

        MODERATION ("MODERATION"),
        BLOCK("BLOCK"),
        ACTIVE("ACTIVE"),
        PUBLISH ("PUBLISH");

        private String type;

    StatusDto(String type) {
            this.type = type;
        }

}
