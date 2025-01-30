package com.example.topichubbackend.controller.mvc;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.*;

@RequestMapping("admin/author")
@Controller
@AllArgsConstructor
public class ManageAuthorController {

    private final IAuthService authService;

    @GetMapping("/fetch")
    public String getAuthor(
            @RequestParam(value = "status", defaultValue = "ACTIVE") String status,
            Model model,
            @RequestParam(value = "page", defaultValue = "1") Integer number,
            @RequestParam(value = "size", defaultValue = "10") Integer size
            ){
        if(HttpRequestUtils.contains(status)){
            Pageable pageable = PageRequest.of(number==0 ? number : number -1, size);
            var userListPage = authService.fetch(status, pageable);
            model.addAttribute("authors", userListPage.getContent());
            model.addAttribute("page", userListPage);
            model.addAttribute("status", status);
            model.addAttribute("size", size);
            return "admin/author";
        }else{
            ErrorDto errorDto = ErrorDto.builder()
                    .message("GET: admin/author ")
                    .build();
            model.addAttribute("error", errorDto);
            return "error/400";
        }
    }
    @PostMapping("/delete")
    public String deleteAuthor(
        @RequestParam("id") String id,
        @RequestParam(value = "status", defaultValue = "ACTIVE") String status,
        @RequestParam(value = "page", defaultValue = "1") String number,
        @RequestParam(value = "size", defaultValue = "10") String size
    ){
        String redirectUrl = UriComponentsBuilder
                .fromUriString("/admin/author/fetch")
                .queryParam("page", number)
                .queryParam("status", status)
                .queryParam("size", size)
                .toUriString();
            authService.delete(id);
            return "redirect:"+redirectUrl;

    }
    @PostMapping("/status")
    public String manageStatus(
            @RequestParam("id") String id,
            @RequestParam(value = "status") String status,
            @RequestParam(value = "page", defaultValue = "1") String number,
            @RequestParam(value = "size", defaultValue = "10") String size,
            Model model
    ){
        if(HttpRequestUtils.contains(status)){
            String redirectUrl = UriComponentsBuilder
                    .fromUriString("/admin/author/fetch")
                    .queryParam("page", number)
                    .queryParam("status", status)
                    .queryParam("size", size)
                    .toUriString();
            authService.manageBlock(id, status);
            return "redirect:"+redirectUrl;
        }else{
            ErrorDto errorDto = ErrorDto.builder()
                    .message("GET: admin/author ")
                    .build();
            model.addAttribute("error", errorDto);
            return "error/400";
        }
    }
}
