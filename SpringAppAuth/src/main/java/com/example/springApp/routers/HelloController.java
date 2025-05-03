package com.example.springApp.routers;


import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
public class HelloController {
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/")
    @Operation(summary = "get a test message")
    public Map<String, String> hello() {
        return Map.of("message", "hello world!");
    }
}
