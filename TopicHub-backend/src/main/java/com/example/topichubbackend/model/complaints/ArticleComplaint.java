package com.example.topichubbackend.model.complaints;

import com.example.topichubbackend.model.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="complaint_article")
public class ArticleComplaint {
    @Column(name = "id")
    @Id
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private User author;

}
