package com.example.topichubbackend.facade.impl;

import com.example.topichubbackend.facade.*;
import com.example.topichubbackend.services.impls.*;

public class RegisterAndSessionUserFacade implements IRegisterAndSessionUserFacade {

    private final static RegisterAndSessionUserFacade facade = new RegisterAndSessionUserFacade();
    private RegisterAndSessionUserFacade() { }
    public static RegisterAndSessionUserFacade  getInstance(){
        return facade;
    }

}
