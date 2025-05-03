package com.example.springApp.routers;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springApp.DTOs.LoginDTO;
import com.example.springApp.entities.UserEntity;
import com.example.springApp.repository.UserRepository;
import com.example.springApp.util.JwtUtil;
import com.example.springApp.util.Util;

import io.micrometer.common.lang.NonNull;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping
    @Operation(summary = "login")
    public Map<String, Object> login(@RequestBody @NonNull LoginDTO data){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("message", "error on login");

        Optional<UserEntity> userData = userRepository.findByEmailAndPassword(data.getEmail(), Util.hashPassword(data.getPassword()));
        if(!userData.isPresent())
            return result;
        
        String token = jwtUtil.generateToken(userData.get());
        System.out.println("TOKEN: " + token + "\n");
        System.out.println(jwtUtil.extractSubject(token) + "\n");

        result.put("token", token);
        result.put("message", "sucess on login");
        return result;
    }
}
