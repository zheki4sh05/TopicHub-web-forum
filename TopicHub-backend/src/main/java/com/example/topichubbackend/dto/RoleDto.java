package com.example.topichubbackend.dto;

public enum RoleDto {
    ADMIN ("ADMIN"),
    USER ("USER");

    private String type;

    RoleDto(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}

