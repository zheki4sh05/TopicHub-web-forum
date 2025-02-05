package com.example.topichubbackend.controller.mvc;
import com.example.topichubbackend.config.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.*;

import java.util.*;

import static com.example.topichubbackend.util.HttpRequestUtils.getClientUrl;

@Controller
@RequestMapping("/admin/article")
@AllArgsConstructor
@Slf4j
public class ArticleModerationController {

    private final IArticleService articleService;
    private final IHubService hubService;
    private final CustomSecurityExpression customSecurityExpression;

    @GetMapping("/fetch")
    public String getModeration(
            @RequestParam Map<String, String> reqParam,
            Model model
            ){
        var articleFilter = HttpRequestUtils.parseFilterParams(reqParam);
        articleFilter.setUserId(customSecurityExpression.getUserId());
        ArticleBatchDto articles = articleService.fetch(articleFilter);
        log.debug("found articles:{}", articles.getArticleDtoList());
        model.addAttribute("batch", articles);
        model.addAttribute("status", articleFilter.getStatus());
        model.addAttribute("returnLink",getClientUrl());
        return "admin/article/index";
    }
    @GetMapping("/view")
    public String getModeration(
            @ModelAttribute("articleStatus") ArticleStatusDto articleStatusDto,
            Model model
    ){
        ArticleDto articleDto = articleService.findById(articleStatusDto.getId());
        List<HubDto> hubDtos = hubService.findAll();
        model.addAttribute("returnLink",getClientUrl());
        model.addAttribute("article", articleDto);
        model.addAttribute("hub", hubDtos.stream()
                .filter(item->item.getId().equals(articleDto.getHub().toString()
                )).findFirst().get());

        String status;
        if(articleStatusDto.getStatus().isEmpty()){
            status = articleService.getStatusNameById(articleStatusDto.getId());
        }else{
            status = articleStatusDto.getStatus();
        }
        model.addAttribute("status", status);
        model.addAttribute("page", articleStatusDto.getPage());
        return "admin/article/overview";
    }
    @PostMapping("/status")
    public String updateStatus(
            @ModelAttribute("articleStatus") ArticleStatusDto articleStatusDto
    ){
        articleService.update(articleStatusDto);
        return back(articleStatusDto.getPage(), articleStatusDto.getStatus());
    }
    @PostMapping("/del")
    public String deleteStatus(
            @ModelAttribute("articleStatus") ArticleStatusDto articleStatusDto
    ){
        articleService.deleteAdmin(articleStatusDto.getId());
        return back(articleStatusDto.getPage(), articleStatusDto.getStatus());
    }

    private String back(Integer page, String status){
        return "redirect:"+UriComponentsBuilder
                .fromUriString("/admin/article/fetch")
                .queryParam("page", page)
                .queryParam("status", status)
                .toUriString();
    }


}
