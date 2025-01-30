package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@Slf4j
@RequestMapping("/api/v1/complaint")
@AllArgsConstructor
@RestController
public class ComplaintController {

    private final IComplaintControl complaintControl ;
    private final IAuthService authService;
    private final CustomSecurityExpression customSecurityExpression;

    @PostMapping("/")
    public ResponseEntity<?> doPost(@RequestBody  ComplaintDto complaintDto)  {

            log.info("new complaint: {}", complaintDto);
            String userId = customSecurityExpression.getUserId();
            complaintControl.create(userId, complaintDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<?> doGet(@RequestParam("type") @NonNull  String type)  {

        List<ComplaintDto> complaintDtoList = complaintControl.findAllByType(type);
        return new ResponseEntity<>(complaintDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> doDelete(
            @RequestParam("id") @NonNull  String complaintId,
            @RequestParam("type") @NotNull String type

                                      ) {
        complaintControl.deleteById(complaintId,type);
        return new ResponseEntity<>(complaintId, HttpStatus.OK);
    }

}
