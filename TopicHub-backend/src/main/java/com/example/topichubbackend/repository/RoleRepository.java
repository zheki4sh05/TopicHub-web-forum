package com.example.topichubbackend.repository;

import com.example.topichubbackend.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("""

select r 
from Role r 
where r.name = :type

""")
    Role findRoleByType(@Param("type") String type);
}
