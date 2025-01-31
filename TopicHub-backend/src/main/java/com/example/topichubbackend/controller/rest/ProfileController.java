package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;


@RequestMapping("/api/v1/profile")
@RestController
@Slf4j
@AllArgsConstructor
public class ProfileController {
    private final IArticleService articleService;
    private final IAuthService authService;
    private final IImageService imageService;
    private final CustomSecurityExpression customSecurityExpression;
    @GetMapping("/")
    public ResponseEntity<?> doGet(
            @RequestParam("type") @NotNull String type,
            @RequestParam("page") Integer page

    )  {
            String userId = customSecurityExpression.getUserId();
            switch (type) {
                case "articles" -> {
                    ArticleFilterDto articleFilterDto = ArticleFilterDto.builder()
                            .page(page)
                            .userId(userId)
                            .build();
                    return new ResponseEntity<>(articleService.fetch(articleFilterDto), HttpStatus.OK);
                }
                case "profile" -> {
                    return new ResponseEntity<>(authService.findById(userId), HttpStatus.OK);
                }
                default -> {
                    return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
                }
            }
        }

    @DeleteMapping("/")
    public ResponseEntity<?> doDelete(
            @RequestParam("type") @NotNull String type,
            @RequestParam("id") String id
    ) {
            String userId = customSecurityExpression.getUserId();

                switch (type){
                    case "article"->{
                            articleService.delete(id, userId);
                            return new ResponseEntity<>(id, HttpStatus.OK);
                    }
                    case "author"->{
                        authService.delete(userId);
                        return new ResponseEntity<>(userId, HttpStatus.OK);
                    }
                    default-> {
                        return new ResponseEntity<>("", HttpStatus.OK);
                    }

                }
    }

    @PutMapping("/")
    public ResponseEntity<?> doPut(
            @RequestBody UserDto userDto
    ) {
        String userId = customSecurityExpression.getUserId();
            authService.updateUser(userDto, userId);
         return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<?> doPost(
            @RequestParam("file") @Size(min = 1, max = 1) @NotNull MultipartFile filePart
            ){
        String userId = customSecurityExpression.getUserId();


                try (InputStream fileContent = filePart.getInputStream()) {
                    imageService.save(userId, fileContent);
                    return new ResponseEntity<>("", HttpStatus.CREATED);
                }
                catch(IOException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
    }
}
