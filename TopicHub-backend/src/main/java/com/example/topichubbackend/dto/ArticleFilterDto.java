package com.example.topichubbackend.dto;

import com.example.topichubbackend.dto.filter.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}
