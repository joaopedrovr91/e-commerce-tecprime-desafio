package com.e_commerce_tecprime_service.controller;

import com.e_commerce_tecprime_service.dto.LoginRequest;
import com.e_commerce_tecprime_service.dto.LoginResponse;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());

            if (user != null) {
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setToken("login_success"); // Não estamos usando JWT, mas retornamos algo para indicar sucesso
                return ResponseEntity.ok(loginResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            logger.error("Error during login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        logger.info("Received register request: {}", registerRequest);
        try {
            User registeredUser = userService.registerUser(registerRequest);
            logger.info("User registered successfully: {}", registeredUser);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error registering user", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}