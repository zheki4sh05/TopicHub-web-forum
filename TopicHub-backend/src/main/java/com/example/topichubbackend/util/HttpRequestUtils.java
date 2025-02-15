package com.example.topichubbackend.util;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.security.util.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class HttpRequestUtils {

    @Value("${client.hostName}")
    private String host;

    @Value("${client.port}")
    private String port;

    // Почему про lombok забыл, или так надо?
    public HttpRequestUtils() {
    }

    // То же самое
    public HttpRequestUtils(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public static ArticleFilterDto parseFilterParams(Map<String, String> reqParam) {
        // Можно вынести "reqParam.get("hub") == null ? null : Integer.valueOf(reqParam.get("hub"))" в переменную:

        // Integer hubValue = reqParam.get("hub") == null ? null : Integer.valueOf(reqParam.get("hub"));

        // и потом присваивать hubValue для param и hub, чтобы два раза не обращатся к параметру
        // и повторно не проверять, равен ли он нулю или нет

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
    public String getClientUrl(){
        return "http://"+host+":"+port;
    }

    // Можно через Stream API (anyMatch, по-моему)
    public Boolean isPublic(HttpServletRequest httpRequest){
        var path = httpRequest.getServletPath();
        for (PublicPath p : PublicPath.values()) {
            if (path.contains(p.type())) {
                return true;
            }
        }
        return false;
    }




}
