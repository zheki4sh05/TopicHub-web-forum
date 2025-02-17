package com.example.topichubbackend.security.util;

import com.example.topichubbackend.model.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;

@Service("cse")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    public User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public String getUserId(){
        return getPrincipal().getUuid().toString();
    }


}
