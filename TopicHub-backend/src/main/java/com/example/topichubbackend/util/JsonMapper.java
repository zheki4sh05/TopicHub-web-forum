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
            BufferedReader reader = request.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                requestBody.append(line);
            }
            if (requestBody.length() > 0) { // Данные в request body существуют
                return Optional.of(mapper.readValue(requestBody.toString(), cls));
            }
            else { // Данных в request body нет
                return Optional.empty();
            }
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