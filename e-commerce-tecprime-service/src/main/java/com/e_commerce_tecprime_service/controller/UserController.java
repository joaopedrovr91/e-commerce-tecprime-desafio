package com.e_commerce_tecprime_service.controller;

import com.e_commerce_tecprime_service.dto.LoginRequest;
import com.e_commerce_tecprime_service.dto.RegisterRequest;
import com.e_commerce_tecprime_service.entity.User;
import com.e_commerce_tecprime_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        try {
             User user = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
             return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        logger.info("Received register request: {}", registerRequest); // Log da requisição recebida
        try {
            User registeredUser = userService.registerUser(registerRequest);
            logger.info("User registered successfully: {}", registeredUser); // Log de sucesso
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error registering user", e); // Log de erro
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}