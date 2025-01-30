package com.example.topichubbackend.util;

import com.example.topichubbackend.dto.*;

import java.util.*;

public class HttpRequestUtils {
    public static ArticleFilterDto parseFilterParams(Map<String, String> reqParam) {
        return ArticleFilterDto.builder()
                .month(reqParam.get("month"))
                .year(reqParam.get("year"))
                .rating(reqParam.get("rating"))
                .page(Integer.valueOf(reqParam.get("page")))
                .hub(Integer.valueOf(reqParam.get("hub")))
                .build();
    }
    public static boolean contains(String status) {

        for (StatusDto c : StatusDto.values()) {
            if (c.name().equals(status)) {
                return true;
            }
        }
        return false;
    }

}
