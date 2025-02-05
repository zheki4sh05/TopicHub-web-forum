package com.example.topichubbackend.controller.rest;


import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.security.util.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing user complaints.
 * Provides endpoints for creating, retrieving, and deleting complaints.
 *
 * <p>This controller is responsible for managing user complaints, including creating new complaints,
 * retrieving complaints by type, and deleting complaints. It uses the complaint control service to handle
 * the business logic and the authentication service for user validation.
 *
 */
@Slf4j
@RequestMapping("/api/v1/complaint")
@AllArgsConstructor
@RestController
public class ComplaintController {

    private final IComplaintControl complaintControl;
    private final CustomSecurityExpression customSecurityExpression;

    /**
     * Creates a new complaint for the authenticated user.
     *
     * <p>This endpoint allows an authenticated user to create a new complaint. The complaint details should
     * be provided in the request body. The user ID is automatically retrieved using the custom security expression.
     *
     * @param complaintDto a request object containing the details of the complaint.
     * @return a ResponseEntity with a 201 Created status indicating the complaint was successfully created.
     * @see ComplaintDto
     */
    @PostMapping("/")
    public ResponseEntity<?> doPost(@RequestBody ComplaintDto complaintDto)  {
        log.info("new complaint: {}", complaintDto);
        String userId = customSecurityExpression.getUserId();
        complaintControl.create(userId, complaintDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Retrieves complaints based on the complaint type.
     *
     * <p>This endpoint allows users to retrieve complaints of a specific type. The type of the complaint
     * should be provided as a request parameter. Results are paginated, with a default page size of 15 items per page.
     *
     * @param type the type of the complaint to filter by.
     * @return a ResponseEntity containing a page of complaints of the specified type.
     * @see ComplaintDto
     */
    @GetMapping("/")
    public ResponseEntity<?> doGet(@RequestParam("type") @NonNull String type)  {
        Pageable pageable = PageRequest.of(1, 15); // Default page size 15
        Page<ComplaintDto> complaintDtoList = complaintControl.findAllByType(type, pageable);
        return new ResponseEntity<>(complaintDtoList, HttpStatus.OK);
    }

    /**
     * Deletes a complaint based on the complaint ID and type.
     *
     * <p>This endpoint allows the user to delete a specific complaint by providing the complaint ID and the type.
     *
     * @param complaintId the ID of the complaint to be deleted.
     * @param type the type of the complaint.
     * @return a ResponseEntity with the complaint ID and a 200 OK status indicating the complaint was successfully deleted.
     */
    @DeleteMapping("/")
    public ResponseEntity<?> doDelete(
            @RequestParam("id") @NonNull String complaintId,
            @RequestParam("type") @NotNull String type
    ) {
        complaintControl.deleteById(complaintId, type);
        return new ResponseEntity<>(complaintId, HttpStatus.OK);
    }

}

