package com.e_commerce_tecprime_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "lat")
    private String lat;

    @Column(name = "longitude")
    private String longitude;

    @Version // Adicione o versionamento otimista
    private Integer version;
}