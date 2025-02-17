package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;
import org.springframework.data.domain.*;

public interface IArticleComplaintService {
    ComplaintDto create(ComplaintDto complaintDto);

    ComplaintDto findById(String id);

    PageResponse<ComplaintDto> findAll(Pageable pageable);

    String deleteById(String id);
}
