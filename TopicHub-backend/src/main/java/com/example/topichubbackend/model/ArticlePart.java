package com.example.topichubbackend.model;

import com.example.topichubbackend.model.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

// Объясни, пж, как связаны ArticlePart и ArticlePartEntity, и зачем такая архитектура.
// Вроде маппинг на одну и ту же таблицу, почти одинаковые поля, зачем пока не понял
@Entity
@Table(name = "articlepart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArticlePart {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private String type;

    @EqualsAndHashCode.Include
    @Column(name = "val")
    private String value;

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private Long created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article", insertable = false, updatable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article")
    private ArticleEntity articleEntity;
}
