package com.example.topichubbackend.dto;

import lombok.*;
import org.springframework.data.domain.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class PageResponse<R> {
    private List<R> items;
    private Integer page;
    private Long total;
    private Integer maxPage;
    public static <T,R> PageResponse<R> map(Function<T, R> mapper, Page<T> page){
        var items = page.getContent();
        return PageResponse.<R>builder()
                .items(items.stream().map(mapper).collect(Collectors.toList()))
                .total(page.getTotalElements())
                .page(page.getNumber())
                .maxPage(page.getTotalPages())
                .build();
    }
}
