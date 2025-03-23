package com.e_commerce_tecprime_service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/populate")
public class PopulateController {

    private final ProductService productService;

    @Autowired
    public PopulateController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> populate() {
        productService.populateDatabase();
        return ResponseEntity.ok("Database population initiated.");
    }
}