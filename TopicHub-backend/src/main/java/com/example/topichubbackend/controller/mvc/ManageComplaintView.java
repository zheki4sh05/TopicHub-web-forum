package com.example.topichubbackend.controller.mvc;


import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
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
public class ManageComplaintView {

    private final IComplaintControl complaintControl;
    private final HttpRequestUtils httpRequestUtils;
    @GetMapping("/fetch")
    public String getComplaints(
            Model model
    ){

        model.addAttribute("returnLink",httpRequestUtils.getClientUrl());
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
        ComplaintDto complaintDto = complaintControl.findById(id,type);
        model.addAttribute("complaint", complaintDto);
        model.addAttribute("page", page);
        model.addAttribute("total",size);
        model.addAttribute("type", type);
        model.addAttribute("returnLink",httpRequestUtils.getClientUrl());
        return "admin/complaint/overview";
    }
}
