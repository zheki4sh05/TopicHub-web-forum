package com.example.topichubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "articlepart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticlePart {
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    @Id
    private UUID uuid;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private String value;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "article")
    private Article article;
}
