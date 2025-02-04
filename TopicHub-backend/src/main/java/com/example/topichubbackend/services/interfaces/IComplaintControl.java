package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;
import org.springframework.data.domain.*;

public interface IComplaintControl {
    void create(String userId, ComplaintDto complaintDto);

    ComplaintDto findById(String id, String type);

    Page findAllByType(String type, Pageable of);

    void deleteById(String complaintId,String type);
}
