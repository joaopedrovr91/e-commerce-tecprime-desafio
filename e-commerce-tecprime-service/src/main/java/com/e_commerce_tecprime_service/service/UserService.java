package com.e_commerce_tecprime_service.service;

import com.e_commerce_tecprime_service.dto.RegisterRequest;
import com.e_commerce_tecprime_service.entity.User;
import com.e_commerce_tecprime_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public User registerUser(RegisterRequest registerRequest) {
        logger.info("Registering user: {}", registerRequest);
        try {
            User newUser = new User();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            newUser.setEmail(registerRequest.getEmail());
            newUser.setName(registerRequest.getName());
            newUser.setPhone(registerRequest.getPhone());
            newUser.setCity(registerRequest.getCity());
            newUser.setStreet(registerRequest.getStreet());
            newUser.setNumber(registerRequest.getNumber());
            newUser.setZipcode(registerRequest.getZipcode());

            User savedUser = userRepository.save(newUser);
            logger.info("User saved successfully: {}", savedUser);
            return savedUser;

        } catch (Exception e) {
            logger.error("Error saving user", e);
            throw e;
        }
    }
}