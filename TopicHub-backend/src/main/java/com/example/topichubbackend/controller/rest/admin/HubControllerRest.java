package com.example.topichubbackend.controller.rest.admin;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/hub")
public class HubControllerRest {

    private final IHubService hubService;

    @PostMapping("/create")
    public ResponseEntity<HubDto> createHub(
            @Valid @RequestBody HubDto hubDto
    ){
        return new ResponseEntity<>(hubService.create(hubDto), HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<HubDto> update(
            @Valid @RequestBody HubDto hubDto

    ){
        return new ResponseEntity<>(hubService.update(hubDto), HttpStatus.OK);
    }
    @DeleteMapping("")
    public ResponseEntity<String> delete(
            @RequestParam(value = "id") String id
    ){
        hubService.delete(Long.valueOf(id));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
