package com.example.topichubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="article")
public class Article {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "theme")
    private String theme;
    @Column(name = "keywords")
    private String keyWords;
    @Column(name = "likes")
    private Long likes;
    @Column(name = "dislikes")
    private Long dislikes;


}