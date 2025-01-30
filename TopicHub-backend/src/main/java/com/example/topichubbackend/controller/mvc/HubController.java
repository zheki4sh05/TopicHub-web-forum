package com.example.topichubbackend.controller.mvc;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.annotation.security.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.access.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

@Controller
@RequestMapping("/admin/hub")
@AllArgsConstructor
public class HubController{

    private final IHubService hubService;

    @PostMapping("/create")
    public String createHub(
            @Valid @ModelAttribute("hubDto") HubDto hubDto
    ){
        hubService.create(hubDto);
        return "redirect:/admin/hub/fetch";
    }
    @GetMapping("/fetch")
    public String fetchAll(Model model){
        var list = hubService.findAll();
        model.addAttribute("hubs", list);
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
        return "admin/hub/update-hub";
    }
    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("hubDto") HubDto hubDto,
            Model model
                          ){
        var updated = hubService.update(hubDto);
        model.addAttribute("hub", updated);
        return "admin/hub/update-hub";
    }
    @PostMapping("/delete")
    public String delete(
            @RequestParam("id") String id
    ){
      hubService.delete(Long.valueOf(id));
        return "redirect:/admin/hub/fetch";
    }
}
