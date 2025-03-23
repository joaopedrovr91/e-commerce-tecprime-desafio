package com.e_commerce_tecprime_service.populateFakeStore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FakeStoreUser {

    private Integer id;
    private String email;
    private String username;
    private String password;
    private Name name;
    private String phone;
    private Address address;

    @Data
    public static class Name {
        private String firstname;
        private String lastname;
    }

    @Data
    public static class Address {
        private String city;
        private String street;
        private Integer number;
        private String zipcode;
        private Geolocation geolocation;
    }

    @Data
    public static class Geolocation {
        private String lat;
        private String longitude;
    }
}