package com.example.topichubbackend.controller.rest;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;



@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController{

    private final IImageService imageService;

    @GetMapping("/")
    public ResponseEntity<?> getImage(@RequestParam("user")
                         @NotNull String userId) {
                byte[] imageData =  imageService.fetch(userId);
        Resource resource = new ByteArrayResource(imageData);
                return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
