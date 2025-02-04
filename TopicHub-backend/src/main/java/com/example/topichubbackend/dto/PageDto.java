package com.example.topichubbackend.dto;

import lombok.*;

import java.util.*;

@Builder
@Getter
public class PageDto<T> {
    private List<T> content;
    private Long total;
    private Integer pageNumber;
    private Integer lastPage;
}
