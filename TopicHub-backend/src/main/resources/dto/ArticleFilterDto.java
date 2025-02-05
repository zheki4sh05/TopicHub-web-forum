package com.example.topichubbackend.dto;

import com.example.topichubbackend.dto.filter.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ArticleFilterDto implements IBusinessLogicFilterSupplier, IFactoryFilterDataSupplier {
    private String month;
    private String year;
    private String rating;
    private Integer page;

    private Integer size = 15;
    private String status;
    private String userId;
    private Integer param;
    private Integer hub;
    private String authorId;
    private String type;

    @Override
    public Integer getParam() {
        return param;
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
}
