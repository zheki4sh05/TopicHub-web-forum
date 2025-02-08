package com.example.topichubbackend.controller.rest.admin;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/author")
public class ManageAuthorControllerRest {

    private final IAuthorService authService;

    @GetMapping("/fetch")
    public ResponseEntity<?> getAuthor(
            @RequestParam(value = "status", defaultValue = "ACTIVE") String status,
            @RequestParam(value = "page", defaultValue = "1") Integer number,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        if(HttpRequestUtils.contains(status)){
            Pageable pageable = PageRequest.of(number==0 ? number : number -1, size);
            var userListPage = authService.fetch(status, pageable);
            return new ResponseEntity<>(userListPage, HttpStatus.OK);
        }else{
            throw new BadRequestException();

        }
    }
    @PostMapping("/delete")
    public  ResponseEntity<?> deleteAuthor(
            @RequestParam("id") String id
    ){
        authService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<?> manageStatus(
            @RequestParam("id") String id,
            @RequestParam(value = "status") String status
    ){
        if(HttpRequestUtils.contains(status)){
            authService.manageBlock(id, status);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }else{
           throw new BadRequestException();
        }
    }

}
