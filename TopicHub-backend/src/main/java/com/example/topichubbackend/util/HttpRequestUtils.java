package com.example.topichubbackend.util;

import com.example.topichubbackend.dto.*;

import java.util.*;

public class HttpRequestUtils {

    private HttpRequestUtils() {
    }

    public static ArticleFilterDto parseFilterParams(Map<String, String> reqParam) {
        return ArticleFilterDto.builder()
                .month(reqParam.get("month"))
                .year(reqParam.get("year"))
                .rating(reqParam.get("rating"))
                .userId(reqParam.get("userId"))
                .status(reqParam.get("status") == null ? StatusDto.PUBLISH.type() : reqParam.get("status"))
                .param(reqParam.get("hub") == null ? null : Integer.valueOf(reqParam.get("hub")))
                .page( reqParam.get("page") == null ? 1 : Integer.parseInt(reqParam.get("page")))
                .hub(reqParam.get("hub") ==null ? null : Integer.valueOf(reqParam.get("hub")))
                .build();
    }
    public static SearchDto parseSearchParams(Map<String, String> reqParam){
        return SearchDto.builder()
                .author(reqParam.get("author"))
                .theme(reqParam.get("theme"))
                .keywords(reqParam.get("keywords"))
                .articleFilterDto(parseFilterParams(reqParam))
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

    public static String getClientUrl(){
        return "http://"+System.getenv("FRONT")+":"+System.getenv("FRONT_HOST");
    }
    public static String getClientUrl(String host, String port){
        return "http://"+host+":"+port;
    }


}
