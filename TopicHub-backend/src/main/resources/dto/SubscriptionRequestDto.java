package com.example.topichubbackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscriptionRequestDto {

    @NotEmpty
    private String author;
}
