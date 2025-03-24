package com.e_commerce_tecprime_service.service;

import com.e_commerce_tecprime_service.dto.RegisterRequest;
import com.e_commerce_tecprime_service.entity.User;
import com.e_commerce_tecprime_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(RegisterRequest registerRequest) {
        logger.info("Registering user: {}", registerRequest);
        try {
            User newUser = new User();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());
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

    public User loginUser(String username, String password) {
        String trimmedUsername = username.trim();
        String trimmedPassword = password.trim();

        logger.info("Attempting login for user: {}", trimmedUsername);
        logger.info("Password recebida: {}", trimmedPassword);

        User user = userRepository.findByUsername(trimmedUsername);

        if (user != null) {
            logger.info("Usuário encontrado no banco de dados: {}", user.getUsername());
            logger.info("Senha do banco de dados: {}", user.getPassword());
        } else {
            logger.warn("Usuário não encontrado no banco de dados.");
        }

        if (user != null && user.getPassword().equals(trimmedPassword)) {
            logger.info("User {} logged in successfully.", trimmedUsername);
            return user;
        } else {
            logger.warn("Login failed for user: {}", trimmedUsername);
            return null; // Ou lançar uma exceção, dependendo da sua necessidade
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}