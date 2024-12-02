package com.example.topichubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Article {
    private Long id;
    private String theme;
    private String keyWords;
    private List<ArticlePart> articlePartList;
}
