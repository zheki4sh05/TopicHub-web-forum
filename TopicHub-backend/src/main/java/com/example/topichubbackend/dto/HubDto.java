package com.example.topichubbackend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HubDto {
    private String id;
    private String name;
    private String en;
    private String ru;
}
