package com.example.topichubbackend.dao.interfaces;

import com.example.topichubbackend.entity.*;

import java.util.*;

public interface HubRepository {
    List<Hub> fetchAll();

    Hub save(Hub hub);

    Optional<Hub> findById(Integer hubId);

    void delete(Hub hub);

    void update(Hub hub);
}
