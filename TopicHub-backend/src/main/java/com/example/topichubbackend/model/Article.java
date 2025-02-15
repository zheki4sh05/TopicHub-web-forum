package com.example.topichubbackend.model;

// Импорты всего (*) наверное избыточны (это и к другим классам из пакета model можно отнести)
// лучше наверное конкретные аннотации импортить, если у тебя немного их используется, а не весь пакет
import jakarta.persistence.*;

// Ну либо это как минимум лишнее, если у тебя есть jakarta.persistence.*
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;

import lombok.*;
import org.hibernate.annotations.*;

import java.sql.*;
import java.util.*;

@Immutable
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

// С неймингом подушню опять, больше ничего не умею, все слишком идеально)))
// Есть просто ArticleEntity, который маппится на табличку "article",
// а entity, которая так и называется Article, маппится на другую таблицу "article_info".
// Меня смутило чутка
@Table(name="article_info")
@NamedEntityGraph(name = "article.articlePartList", attributeNodes = @NamedAttributeNode("articlePartList"))
public class Article {

    // Не подскажешь потом, почему у тебя в некоторых Entity есть @GeneratedValue
    // с id-шкой, а где-то не ставишь
    @Id
    @Column(name = "id")
    private Long id;

    // Это ко всем классам из model относится.
    // Я понимаю, зачем так делаешь и наверное так более правильно и чисто всегда указывать @Column,
    // даже когда имена полей и так совпадают. Но если ты никаких других аргументов в @Column, кроме
    // 'name', не указываешь и название поля совпадает с колонкой, то можно было бы и опустить эту аннотацию
    // (но поправь меня и поставь на место, если я не прав, пж)
    @Column(name = "theme")
    private String theme;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "dislikes")
    private Integer dislikes;

    @Column(name = "comments_count")
    private Integer comments;

    @Column(name = "keywords")
    private String keyWords;

    @Column(name = "status")
    private String status;

    // Можно @Setter над state поставить, чтобы уже везде Lombok был
    public void setState(Integer state) {
        this.state = state;
    }

    @Transient
    private Integer state;

    @ManyToOne
    @JoinColumn(name = "hub")
    private Hub hub;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created")
    private Timestamp created;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArticlePart> articlePartList;
}
