package com.example.topichubbackend.controller.mvc;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import static com.example.topichubbackend.util.HttpRequestUtils.getClientUrl;

@Controller
@RequestMapping("/admin/hub")
@AllArgsConstructor
public class HubController{

    private final IHubService hubService;

    @PostMapping("/create")
    public String createHub(
            @Valid @ModelAttribute("hubDto") HubDto hubDto,
            Model model
    ){
        hubService.create(hubDto);
        model.addAttribute("returnLink",getClientUrl());
        return "redirect:/admin/hub/fetch";
    }
    @GetMapping("/fetch")
    public String fetchAll(Model model){
        var list = hubService.findAll();
        model.addAttribute("hubs", list);
        model.addAttribute("returnLink",getClientUrl());
        return "admin/hub/main";
    }
    @GetMapping("/create")
    public String edit(){
        return "admin/hub/create-hub";
    }
    @GetMapping("/edit")
    public String edit(
            @RequestParam("id") String id,
            @RequestParam("en") String en,
            @RequestParam("ru") String ru,
            Model model){
        HubDto hubDto = HubDto.builder()
                .id(id)
                .ru(ru)
                .en(en)
                .build();
        model.addAttribute("hub", hubDto);
        model.addAttribute("returnLink",getClientUrl());
        return "admin/hub/update-hub";
    }
    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("hubDto") HubDto hubDto,
            Model model
                          ){
        var updated = hubService.update(hubDto);
        model.addAttribute("hub", updated);
        model.addAttribute("returnLink",getClientUrl());
        return "admin/hub/update-hub";
    }
    @PostMapping("/delete")
    public String delete(
            @RequestParam("id") String id,
            Model model
    ){
        model.addAttribute("returnLink",getClientUrl());
      hubService.delete(Long.valueOf(id));
        return "redirect:/admin/hub/fetch";
    }
}
