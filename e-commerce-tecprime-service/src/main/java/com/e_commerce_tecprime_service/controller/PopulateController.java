package com.e_commerce_tecprime_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce_tecprime_service.populateFakeStore.FakeStoreProduct;
import com.e_commerce_tecprime_service.populateFakeStore.FakeStoreUser;
import com.e_commerce_tecprime_service.service.ProductService;

@RestController
@RequestMapping("/populate")
public class PopulateController {

    private final ProductService productService;

    public PopulateController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> populate() {
        productService.cleanDatabase();
        productService.populateProducts();
        productService.populateUsers();

        return ResponseEntity.ok("Database population initiated successfully.");
    }

    @PostMapping("/more")
    public ResponseEntity<String> populateMore() {
        List<FakeStoreProduct> fakeProducts = productService.generateFakeProducts(50); // Gere 50 produtos
        fakeProducts.forEach(productService::saveProduct);
        List<FakeStoreUser> fakeUsers = productService.generateFakeUsers(50);
        fakeUsers.forEach(productService::saveUser);
    
        return ResponseEntity.ok("More fake data added.");
    }
}