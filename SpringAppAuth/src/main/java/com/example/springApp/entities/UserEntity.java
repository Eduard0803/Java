package com.example.springApp.entities;

import com.example.springApp.DTOs.UserDTO;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    private UserEntity(){}

    public UserEntity(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    public UserEntity(UserDTO data){
        this.email = data.getEmail();
        this.password = data.getPassword();
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}
}
