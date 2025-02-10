package com.example.topichubbackend.util;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderWrapperTest {

    @Test
    void hash() {

        PasswordEncoderWrapper passwordEncoderWrapper = new PasswordEncoderWrapper();
        String pass = "123456";
        String hash = passwordEncoderWrapper.hash(pass);

        assertFalse(hash.isEmpty());
        assertFalse(hash.equals(pass));

        assertTrue(passwordEncoderWrapper.matches(pass, hash));
    }

}