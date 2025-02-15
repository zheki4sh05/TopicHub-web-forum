package com.example.topichubbackend.controller.rest.admin;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j // Аннотацию добавил, но логов нигде нет, надо бы добавить)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/hub")
public class HubControllerRest {

    private final IHubService hubService;

    @PostMapping("/create")
    public ResponseEntity<?> createHub(
            @Valid @RequestBody HubDto hubDto
    ){
        hubService.create(hubDto);
        return new ResponseEntity<>(hubDto, HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<?> update(
            @Valid @RequestBody HubDto hubDto

    ){
        var updated = hubService.update(hubDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
    @DeleteMapping("")
    public ResponseEntity<?> delete(
            @RequestParam(value = "id") String id
    ){
        hubService.delete(Long.valueOf(id));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
