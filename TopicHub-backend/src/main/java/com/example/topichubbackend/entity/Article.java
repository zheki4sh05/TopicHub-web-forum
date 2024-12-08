package com.example.topichubbackend.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.search.engine.backend.types.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;


import java.sql.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Indexed
@Table(name="article")
public class Article {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @FullTextField
    @Column(name = "theme")
    private String theme;

    @FullTextField
    @Column(name = "keywords")
    private String keyWords;

    @Column(name = "likes")
    private Long likes;

    @Column(name = "dislikes")
    private Long dislikes;

    @ManyToOne
    @JoinColumn(name = "hub")
    private Hub hub;

    @ManyToOne
    @JoinColumn(name = "author_id")
//    @IndexedEmbedded(includePaths = "login")
    private User author;

    @Column(name = "created")
    private Timestamp created;


}
