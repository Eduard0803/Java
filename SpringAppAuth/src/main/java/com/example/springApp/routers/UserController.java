package com.example.springApp.routers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springApp.DTOs.UserDTO;
import com.example.springApp.entities.UserEntity;
import com.example.springApp.repository.UserRepository;
import com.example.springApp.util.Util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@RequestMapping("/user")
public class UserController{
    @Autowired
    private UserRepository userRepository;


    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    @Operation(summary = "get all users")
    public List<UserDTO> create(){
        return userRepository.findAll().stream()
            .map(UserDTO::new)
            .toList();
    }

    
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @Operation(summary = "create a new user")
    public Map<String, Object> create(@RequestBody UserDTO data){
        UserEntity userData = new UserEntity(data);
        userData.setPassword(Util.hashPassword(data.getPassword()));

        System.out.println("userData: " + Util.gson.toJson(userData));

        Long id = userRepository.save(userData).getId();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("message", "sucess on create user");
        result.put("id", id);
        return result;
    }
}
