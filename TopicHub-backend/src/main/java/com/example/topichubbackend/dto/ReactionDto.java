package com.example.topichubbackend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReactionDto {
    private String type;
    private String object;
    private String objectId;
}
