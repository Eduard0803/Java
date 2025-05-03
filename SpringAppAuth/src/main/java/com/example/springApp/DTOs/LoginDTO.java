package com.example.springApp.DTOs;

import io.micrometer.common.lang.NonNull;

public class LoginDTO {

    private String email;
    private String password;

    private LoginDTO(){}

    public LoginDTO(@NonNull String email, @NonNull String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}
}
