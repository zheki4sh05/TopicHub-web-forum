package com.example.topichubbackend.controller.mvc;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class WelcomeController {
    @GetMapping("")
    public String welcome(Model model){
        return "admin/index";
    }
}