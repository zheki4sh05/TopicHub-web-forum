package com.example.topichubbackend.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;


import java.sql.*;
import java.util.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Indexed
@Table(name="article")
@NamedEntityGraph( name = "article.articlePartList",
        attributeNodes = @NamedAttributeNode("articlePartList") )
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

    @ManyToOne
    @JoinColumn(name = "hub")
    private Hub hub;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created")
    private Timestamp created;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticlePart> articlePartList;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Likes> likesList;
}
