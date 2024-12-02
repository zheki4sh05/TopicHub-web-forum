package com.example.topichubbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "articlePartType")
public class ArticlePartType {
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "name")
    @Id
    private String name;
}
