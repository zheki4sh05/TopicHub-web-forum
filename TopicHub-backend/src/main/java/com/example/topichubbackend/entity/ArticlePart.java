package com.example.topichubbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "articlePart")
public class ArticlePart {
    @Column(name = "id")
    private Integer id;

    @Column(name = "created")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long created;

    @ManyToOne
    @JoinColumn(name = "type")
    private ArticlePartType articlePartType;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "article")
    private Article article;
}
