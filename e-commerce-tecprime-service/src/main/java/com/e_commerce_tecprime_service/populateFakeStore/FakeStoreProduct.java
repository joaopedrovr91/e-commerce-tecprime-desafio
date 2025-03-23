package com.e_commerce_tecprime_service.populateFakeStore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FakeStoreProduct {

    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}