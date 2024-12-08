package com.example.topichubbackend.util;

import java.io.*;
import java.nio.charset.*;
import java.security.*;
import java.util.*;

public class PasswordEncoder {

    private final static MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getHash(String password, String salt){
        byte[] hash = messageDigest.digest(password.concat(salt).getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

}
