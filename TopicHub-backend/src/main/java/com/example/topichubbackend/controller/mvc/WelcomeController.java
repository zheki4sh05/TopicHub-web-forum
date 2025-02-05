package com.example.topichubbackend.controller.mvc;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import static com.example.topichubbackend.util.HttpRequestUtils.getClientUrl;


@Controller
@RequestMapping("/admin")
public class WelcomeController {
    @GetMapping("")
    public String welcome(Model model){
        model.addAttribute("returnLink",getClientUrl());
        return "admin/index";
    }
}