package com.example.topichubbackend.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.util.*;

@Entity
@Table(name = "articlepart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArticlePart {
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    @Id
    @EqualsAndHashCode.Include
    private UUID uuid;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    @EqualsAndHashCode.Include
    private String value;

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private Long created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article")
    private Article article;
}
