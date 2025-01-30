package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("api/v1/hubs")
@AllArgsConstructor
public class HubsController{

    private final IHubService hubService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<HubDto> hubDtoList = hubService.findAll();
        return new ResponseEntity<>(hubDtoList, HttpStatus.OK);
    }


}
