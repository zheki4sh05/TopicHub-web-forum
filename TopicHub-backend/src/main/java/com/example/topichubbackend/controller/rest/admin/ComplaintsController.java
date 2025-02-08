package com.example.topichubbackend.controller.rest.admin;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/complaints")
public class ComplaintsController {

    private final IComplaintControl complaintControl;

    @GetMapping("/fetch/{type}")
    public ResponseEntity<?> getComplaints(
            @PathVariable("type") String type,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        Pageable pageable = PageRequest.of(page==0 ? page : page -1, size);
        PageResponse<ComplaintDto> complaintDtos =  complaintControl.findAllByType(type, pageable);
        return new ResponseEntity<>(complaintDtos, HttpStatus.OK);
    }

    @DeleteMapping ("/del/{type}")
    public ResponseEntity<?> delComplaint(
            @PathVariable("type") String type,
            @RequestParam(value = "id") String id
    ){
        complaintControl.deleteById(id,type);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
