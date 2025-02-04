package com.example.topichubbackend.controller.mvc;


import com.example.topichubbackend.dto.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.core.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/auth")
@Controller
@Slf4j
public class MVCAuthController {
    @GetMapping("")
    public String welcome(
            @AuthenticationPrincipal UserDetails user
    ){
            if(user.getAuthorities()
                    .stream()
                    .anyMatch(item->item.getAuthority().equals(RoleDto.ADMIN.type()))){
                return "redirect:/admin";
            }else{
                return "redirect:http://localhost:3000";
            }
    }


}
