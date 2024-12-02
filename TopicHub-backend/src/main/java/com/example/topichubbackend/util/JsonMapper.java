package com.example.topichubbackend.util;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

public final class JsonMapper {

    public static Optional<Object> mapFrom(HttpServletRequest request, Class cls){

        ObjectMapper mapper = new ObjectMapper();

        try {
            return Optional.of(mapper.readValue(request.getReader(), cls));

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static <T> String mapTo(T obj){

        ObjectMapper objectMapper  = new ObjectMapper();
        String data=null;
        try {
            data =  objectMapper.writeValueAsString(obj);
            return data;
        }catch (JsonProcessingException e) {

        }
        return "";
    }

}