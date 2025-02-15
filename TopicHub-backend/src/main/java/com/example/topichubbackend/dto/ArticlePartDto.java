package com.example.topichubbackend.dto;

// Пожалуй, импорты * избыточны (это не только к этому классу относится)
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticlePartDto {
    private Integer id;
    private String uuid;
    private Long created;
    private String name;
    private String type;
    private String value;
}
