package com.example.topichubbackend.dto;

import com.example.topichubbackend.dto.filter.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleFilterDto implements IBusinessLogicFilterSupplier, IFactoryFilterDataSupplier {
    private String month;
    private String year;
    private String rating;
    private Integer page;

    // Не лучше константой сдлелать?
    private Integer size = 15;

    // Как-то странно метод среди полей затесался, хоть и рядом с полем, к которому относится, но все же.
    // + зачем вручную сеттеры и геттеры, которые без @Override, можно @Setter, @Getter и над полями
    public String getStatus() {
        return status;
    }

    private String status;
    private String userId;
    private Integer param;
    private Integer hub;
    private String authorId;
    private String type;

    @Override
    public Integer getParam() {
        return hub;
    }

    @Override
    public String getAuthorId() {
        return authorId;
    }

    @Override
    public Integer getMonth() {
        return month==null ? null: Integer.parseInt(month);
    }

    @Override
    public Integer getYear() {
        return year==null ? null: Integer.parseInt(year);
    }
    public Integer size() {
        return size;
    }

    public String getRating() {
        return rating;
    }

    public String getUserId() {
        return userId;
    }

    public int getPage() {
        return page;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
