package com.example.topichubbackend.dto;

// Здесь у меня те же вопросы, что и для RoleDto
public enum StatusDto {

        MODERATION ("MODERATION"),
        BLOCK("BLOCK"),
        ACTIVE("ACTIVE"),
        PUBLISH ("PUBLISH");

        private String type;

    StatusDto(String type) {
            this.type = type;
        }

        public String type() {
            return type;
        }

}
