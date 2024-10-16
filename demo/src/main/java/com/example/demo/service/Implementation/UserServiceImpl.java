package com.example.demo.service.Implementation;


import com.example.demo.dto.RegistrationDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }


    @Override
    public User createUser(User user) {
           return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setName(registrationDto.getName());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

//        // Set default role as "GABINET"
        Role customerRole = roleRepository.findByName("GABINET");
        user.setRoles(new HashSet<>(Collections.singletonList(customerRole)));
        return userRepository.save(user);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User getCurrentUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();
            // Fetch and return the User object based on the username
            return userRepository.findByName(name);
        }
        return null; // Or throw an exception, depending on your needs
    }


    @Override
    public List<User> findUsersByRole(String roleName) {
        return userRepository.findByRoles_Name(roleName);
    }

    @Override
    public User saveUserWithRole(User user, String roleName) {
        // Create the user
        User savedUser = createUser(user);

        // Find the role by name
        Role role = roleRepository.findByName(roleName);

        // Add the role to the user
        if (role != null) {
            savedUser.getRoles().add(role);
            userRepository.save(savedUser);
        } else {
            throw new IllegalArgumentException("Role not found: " + roleName);
        }
        return savedUser;
    }


    @Override
    public User updateUser(Long id, User updateUser){
       Optional<User> optionalUser = userRepository.findById(id);
       if (optionalUser.isPresent()){
           User existingUser = optionalUser.get();
           existingUser.setName(updateUser.getName());
           existingUser.setEmail(updateUser.getEmail());
           existingUser.setPhoneNumber(updateUser.getPhoneNumber());
           existingUser.setAddress(updateUser.getAddress());
           existingUser.setOpeningHours(updateUser.getOpeningHours());
return userRepository.save(existingUser);
       }else {
           return null;
       }}

    @Override
    public String deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Remove roles associated with the user
            user.getRoles().clear();
            userRepository.save(user);

            // Delete the user
            userRepository.deleteById(id);

            return "User with ID " + id + " has been deleted.";
        } else {
            return "User with ID " + id + " not found.";
        }    }


}
