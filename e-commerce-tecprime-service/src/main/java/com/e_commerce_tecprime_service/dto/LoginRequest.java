package com.e_commerce_tecprime_service.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}