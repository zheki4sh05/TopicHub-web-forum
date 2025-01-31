package com.example.topichubbackend.controller.mvc;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin/article")
@AllArgsConstructor
public class ArticleModerationController {

    private final IArticleService articleService;

    @GetMapping("/fetch")
    public String getModeration(
            @RequestParam Map<String, String> reqParam,
            Model model
            ){
        var articleFilter = HttpRequestUtils.parseFilterParams(reqParam);
        var articles = articleService.fetch(articleFilter);
        model.addAttribute("articles", articles);
        return "admin/moderation";
    }
    @PatchMapping("")
    public String updateStatus(
            @ModelAttribute("status") ArticleStatusDto articleStatusDto,
            Model model
    ){
        articleService.update(articleStatusDto);
        return "redirect:/admin/moderation";
    }
    @DeleteMapping("")
    public String deleteStatus(
            @RequestParam("id") String id
    ){
        articleService.deleteAdmin(id);
        return "redirect:/admin/moderation";
    }


}
