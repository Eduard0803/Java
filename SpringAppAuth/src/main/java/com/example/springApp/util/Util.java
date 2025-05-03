package com.example.springApp.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.stereotype.Component;


@Component
public class Util {
    private static final String HASH_PASSWORD_ALGORITHM = "SHA-256"; // "MD5", "SHA-1", "SHA-256", "SHA-512"
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public static String hashPassword(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_PASSWORD_ALGORITHM);
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(byte b: hashBytes)
                sb.append(String.format("%02x", b));
            return sb.toString();
        }catch(Exception e){
            return input;
        }
    }
}
