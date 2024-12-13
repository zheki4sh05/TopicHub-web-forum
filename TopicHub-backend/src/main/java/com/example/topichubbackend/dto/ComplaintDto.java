package com.example.topichubbackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ComplaintDto {
    private String id;

    @NotEmpty
    private String type;

    @NotEmpty
    private String title;

    @NotEmpty
    private String targetId;

    @NotEmpty
    private String body;

}
