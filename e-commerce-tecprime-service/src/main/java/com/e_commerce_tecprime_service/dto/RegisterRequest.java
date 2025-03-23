package com.e_commerce_tecprime_service.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String name;
    private String phone;
    private String city;
    private String street;
    private Integer number;
    private String zipcode;
}