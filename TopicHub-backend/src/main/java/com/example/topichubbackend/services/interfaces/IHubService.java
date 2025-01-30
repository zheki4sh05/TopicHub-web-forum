package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;
import org.springframework.stereotype.*;

import java.util.*;

public interface IHubService {
    List<HubDto> findAll();

    HubDto create(HubDto hubDto);

    void delete(Long hubId);

    HubDto update(HubDto hubDto);
}
