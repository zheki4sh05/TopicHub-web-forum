package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.*;

public class HubService implements IHubService {
    private final static HubService hubService = new HubService();
    private HubService() { }
    public static HubService  getInstance(){
        return hubService;
    }

    private final HubDao hubDao = DaoFactory.createHubDao();

    @Override
    public List<HubDto> findAll() {

        return  hubDao.fetchAll().stream()
                .map(item->HubDto.builder()
                        .id(item.getId().toString())
                        .name(item.getName())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public HubDto create(HubDto hubDto) {

        Hub hub = Hub.builder()
                .name(hubDto.getName())
                .build();

       Hub created = (Hub) hubDao.save(hub);

        return HubDto.builder()
                .id(created.getId().toString())
                .name(created.getName())
                .build();
    }

    @Override
    public void delete(Integer hubId) {
        Hub hub = hubDao.findById(hubId).orElseThrow(EntityNotFoundException::new);
        hubDao.delete(hub);
    }

    @Override
    public HubDto update(HubDto hubDto) {
        Hub hub = hubDao.findById(Integer.valueOf(hubDto.getId())).orElseThrow(EntityNotFoundException::new);
        hub.setName(hub.getName());
        hubDao.merge(hub);
        return hubDto;
    }
}
