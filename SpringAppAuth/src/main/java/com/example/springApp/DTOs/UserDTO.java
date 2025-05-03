package com.example.springApp.DTOs;

import com.example.springApp.entities.UserEntity;

import io.micrometer.common.lang.NonNull;

public class UserDTO {

    private String email;
    private String password;

    public UserDTO(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    public UserDTO(UserEntity user) {
        this.email = user.getEmail();
    }

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}
    
}
