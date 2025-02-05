package com.example.topichubbackend.controller.mvc;


import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.*;

@Controller
@RequestMapping("/admin/complaint")
@AllArgsConstructor
@Slf4j
public class ManageComplaint {

    private final IComplaintControl complaintControl;

    @GetMapping("/fetch/{type}")
    public String getComplaints(
            @PathVariable("type") String type,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            Model model
    ){
        Pageable pageable = PageRequest.of(page==0 ? page : page -1, size);
     Page complaintDtos =  complaintControl.findAllByType(type, pageable);
     log.info("complaints:{}", complaintDtos.getContent());
     model.addAttribute("list", complaintDtos.getContent());
     model.addAttribute("page", complaintDtos.getNumber());
     model.addAttribute("total", complaintDtos.getTotalPages());
     model.addAttribute("type", type);
     return "admin/complaint/index";
    }
    @GetMapping("/view/{type}")
    public String getComplaint(
            @PathVariable("type") String type,
            @RequestParam(value = "number") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "id") String id,
            Model model
    ){
//        if(type.equals("article")){
//            ArticleDto articleDto = articleService.findById(targetId);
//            model.addAttribute("target", articleDto);
//        }else if(type.equals("comment")){
//            CommentDto commentDto = commentsService.findById(targetId);
//            model.addAttribute("target", commentDto);
//        }else{
//            return "error/400";
//        }
        ComplaintDto complaintDto = complaintControl.findById(id,type);
        model.addAttribute("complaint", complaintDto);
        model.addAttribute("page", page);
        model.addAttribute("total",size);
        model.addAttribute("type", type);
        return "admin/complaint/overview";
    }
    @PostMapping ("/del/{type}")
    public String delComplaint(
            @PathVariable("type") String type,
            @RequestParam(value = "number") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "id") String id,
            Model model
    ){
//        if(type.equals("article")){
//            ArticleDto articleDto = articleService.findById(targetId);
//            model.addAttribute("target", articleDto);
//        }else if(type.equals("comment")){
//            CommentDto commentDto = commentsService.findById(targetId);
//            model.addAttribute("target", commentDto);
//        }else{
//            return "error/400";
//        }
         complaintControl.deleteById(id,type);
        model.addAttribute("page", page);
        model.addAttribute("total",size);
        model.addAttribute("type", type);
        return back(page, type);
    }
    private String back(Integer page, String type){
        return "redirect:"+ UriComponentsBuilder
                .fromUriString("/admin/article/fetch/"+type)
                .queryParam("page", page)
                .toUriString();
    }
}
