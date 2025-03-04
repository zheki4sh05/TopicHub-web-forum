package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;
import org.springframework.data.domain.*;

public interface IComplaintControl {
    void create(ComplaintDto complaintDto);

    ComplaintDto findById(String id, String type);

    PageResponse<ComplaintDto> findAllByType(String type, Pageable of);

    void deleteById(String complaintId,String type);
}
