package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;

import java.util.*;

public interface IHubService {
    List<HubDto> findAll();

    HubDto create(HubDto hubDto);

    void delete(Integer hubId);

    HubDto update(HubDto hubDto);
}
