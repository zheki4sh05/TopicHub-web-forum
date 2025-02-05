package com.example.topichubbackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookmarksRequestDto {
    @NotEmpty
    private String article;
}
