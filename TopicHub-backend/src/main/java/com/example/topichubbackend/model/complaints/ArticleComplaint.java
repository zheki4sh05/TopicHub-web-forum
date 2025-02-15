package com.example.topichubbackend.model.complaints;

import com.example.topichubbackend.model.*;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.sql.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
// До нейминга опять декапаюсь, раз уж табличка complaint_article, то и сущность наверное уже стоило
// назвать ComplaintArticle, но это мелочи, думаю
@Table(name="complaint_article")
public class ArticleComplaint {
    @Column(name = "id")
    @Id
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "date")
    private Timestamp date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article")
    private ArticleEntity article;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private User author;

}
