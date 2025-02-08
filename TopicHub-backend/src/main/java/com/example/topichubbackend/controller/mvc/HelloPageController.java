package com.example.topichubbackend.controller.mvc;

import com.example.topichubbackend.util.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
@AllArgsConstructor
public class HelloPageController {

    private final HttpRequestUtils httpRequestUtils;

    @GetMapping("")
    public String hello(Model model){
        model.addAttribute("returnLink",httpRequestUtils.getClientUrl());
        model.addAttribute("adminLink","/auth");
        model.addAttribute("sourceLink","https://github.com/zheki4sh05/TopicHub-web-forum");
        return "index";
    }
}
