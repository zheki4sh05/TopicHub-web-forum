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

    // Вроде можно просто сделать так:
    public String getType() {
        return this.name();// По дефолту вернет строковое представление.
                           // Это хорошо, что ты захотел кастомный метод для этого,
                           // но, как я понял, он имеет ровно такую же реализацию, как и дефолтный name(),
                           // поэтому поле type можно вовсе убрать и этот метод вовсе тоже. Не понял я пока, зачем
                           // кастомный делать
    }

}

