package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.security.util.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;


/**
 * REST Controller for managing user profiles.
 * Provides endpoints for retrieving, updating, deleting, and uploading user profile data.
 *
 * <p>This controller is responsible for handling operations related to user profiles, such as fetching user details,
 * updating user information, deleting articles, and uploading profile images.
 *
 * @author [Your Name]
 * @version 1.0
 * @since 2025-02-04
 */
@RequestMapping("/api/v1/profile")
@RestController
@Slf4j
@AllArgsConstructor
public class ProfileController {
    private final IArticleService articleService;
    private final IAuthorService authService;
    private final IImageService imageService;
    private final CustomSecurityExpression customSecurityExpression;

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(value = "login",required = false,defaultValue="") String login,
            @RequestParam(value = "email",required = false,defaultValue="") String email,
            @RequestParam(value = "page",defaultValue = "1") Integer page
    ){
        if(login.isEmpty() && email.isEmpty()){
            throw new BadRequestException();
        }else{
            PageResponse<UserDto> authorDtoPageResponse = authService.search(login, email,page, false);
            return new ResponseEntity<>(authorDtoPageResponse, HttpStatus.OK);
        }
    }

    /**
     * Retrieves profile or articles based on the provided type and page.
     *
     * <p>This endpoint fetches either user articles or user profile information depending on the type parameter.
     * If the type is "articles", it returns a list of articles for the authenticated user.
     * If the type is "profile", it returns the user's profile information.
     *
     * @param type the type of data to retrieve: either "articles" or "profile".
     * @param page the page number for article pagination.
     * @return a ResponseEntity containing the requested data and a 200 OK status.
     * @see ArticleFilterDto
     * @see UserDto
     */
    @GetMapping("")
    public ResponseEntity<?> doGet(
            @RequestParam("type")  String type,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
    )  {
        String userId = customSecurityExpression.getUserId();
        switch (type) {
            case "articles" -> {
                ArticleFilterDto articleFilterDto = ArticleFilterDto.builder()
                        .page(page)
                        .userId(userId)
                        .authorId(userId)
                        .status(StatusDto.PUBLISH.type())
                        .build();
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(articleService.fetch(articleFilterDto));
            }
            case "profile" -> {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(authService.findById(userId));
            }
            default -> {
                return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Deletes either an article or the user's profile.
     *
     * <p>This endpoint allows the user to delete either an article or their profile.
     * If the type is "article", the specified article is deleted.
     * If the type is "author", the user's profile is deleted.
     *
     * @param type the type of data to delete: either "article" or "author".
     * @param id the ID of the article or user to be deleted.
     * @return a ResponseEntity indicating the result of the delete operation.
     */
    @DeleteMapping("")
    public ResponseEntity<?> doDelete(
            @RequestParam("type") @NotNull String type,
            @RequestParam("id") String id
    ) {
        String userId = customSecurityExpression.getUserId();

        switch (type) {
            case "article" -> {
                articleService.delete(id, userId);
                return new ResponseEntity<>(id, HttpStatus.OK);
            }
            case "author" -> {
                authService.delete(userId);
                return new ResponseEntity<>(userId, HttpStatus.OK);
            }
            default -> {
                return new ResponseEntity<>("", HttpStatus.OK);
            }
        }
    }

    /**
     * Updates the user's profile information.
     *
     * <p>This endpoint allows the user to update their profile details by providing a UserDto object
     * containing the updated information.
     *
     * @param userDto the updated user profile information.
     * @return a ResponseEntity containing the updated user data and a 200 OK status.
     * @see UserDto
     */
    @PutMapping("")
    public ResponseEntity<?> doPut(
            @RequestBody UserDto userDto
    ) {
        String userId = customSecurityExpression.getUserId();
        authService.updateUser(userDto, userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Uploads a profile image for the user.
     *
     * <p>This endpoint allows the user to upload a new profile image. The image file is passed
     * as a multipart file in the request. The image is saved using the image service.
     *

     * @return a ResponseEntity indicating the result of the upload operation.
     * @throws IOException if there is an error during the file upload process.
     */
    @PostMapping("")
    public ResponseEntity<?> doPost(
//            @RequestParam("file") @Size(min = 1, max = 1) @NotNull MultipartFile filePart
             @ModelAttribute UploadImageDto uploadImageDto
    ) {
        String userId = customSecurityExpression.getUserId();

        try (InputStream fileContent = uploadImageDto.getFile().getInputStream()) {
            imageService.save(userId, fileContent);
            return new ResponseEntity<>("", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


