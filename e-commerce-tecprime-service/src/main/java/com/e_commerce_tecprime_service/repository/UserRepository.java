package com.e_commerce_tecprime_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.e_commerce_tecprime_service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}