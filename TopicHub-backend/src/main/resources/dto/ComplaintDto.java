package com.example.topichubbackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ComplaintDto {
    private String id;

    @NotEmpty
    private String type;

    private Timestamp date;

    @NotEmpty
    private String title;

    @NotEmpty
    private String targetId;

    @NotEmpty
    private String body;

    private AuthorDto userDto;

    @Override
    public String toString() {
        return "ComplaintDto{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", targetId='" + targetId + '\'' +
                ", body='" + body + '\'' +
                ", userDto=" + userDto +
                '}';
    }
}
