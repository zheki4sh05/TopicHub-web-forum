package com.example.topichubbackend.dto;

import com.example.topichubbackend.model.*;
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

        // Можно вынести создание PageResponse у 3-х методов и сократить код
        return PageResponse.<R>builder()
                .items(items.stream().map(mapper).collect(Collectors.toList()))
                .total(page.getTotalElements())
                .page(page.getNumber())
                .maxPage(page.getTotalPages())
                .build();
    }

    public static <T,R> PageResponse<R> map(Function<T, R> mapper, PageResponse<T> page){
        var items = page.getItems();
        return PageResponse.<R>builder()
                .items(items.stream().map(mapper).collect(Collectors.toList()))
                .total(page.getTotal())
                .page(page.getPage())
                .maxPage(page.getMaxPage())
                .build();
    }

    public static <T> PageResponse<T> map(Page<T> page){
        return  PageResponse.<T>builder()
                .total(page.getTotalElements())
                .page(page.getNumber())
                .maxPage(page.getTotalPages())
                .items(page.getContent())
                .build();
    }

    @Override
    public String toString() {
        return "PageResponse{" +
                "items=" + items.size() +
                ", page=" + page +
                ", total=" + total +
                ", maxPage=" + maxPage +
                '}';
    }
}
