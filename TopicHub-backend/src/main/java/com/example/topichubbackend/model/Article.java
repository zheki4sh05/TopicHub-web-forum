package com.example.topichubbackend.model;

import jakarta.persistence.*;
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
@Table(name="article_info")
@NamedEntityGraph(name = "article.articlePartList", attributeNodes = @NamedAttributeNode("articlePartList"))
public class Article {
    @Id
    @Column(name = "id")
    private Long id;

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

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<ArticlePart> articlePartList;
}
