package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;

import java.util.*;

public interface IComplaintControl {
    void create(String userId, ComplaintDto complaintDto);

    List<ComplaintDto> findAllByType(String type);

    void deleteById(String complaintId,String type);
}
