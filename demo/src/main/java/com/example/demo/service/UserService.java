package com.example.demo.service;

import com.example.demo.dto.RegistrationDto;
import com.example.demo.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User createUser(User user);
    List<User> getAllUsers();

    User registerUser(RegistrationDto registrationDto);

    User findByName(String name);

    User getCurrentUser(Authentication authentication);


    List<User> findUsersByRole(String roleName);

    User saveUserWithRole(User user, String roleName);

}
