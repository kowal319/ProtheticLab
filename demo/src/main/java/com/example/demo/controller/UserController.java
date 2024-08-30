package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


@GetMapping("users")
    public String listOfUsers(Model model){
    List<User> users = userService.getAllUsers();
    model.addAttribute("users", users);
    return "listOfUsers";
    }

    @GetMapping("profile")
    public String profile(Model model, Authentication authentication){
        String loggedInUsername = authentication.getName();
        User currentUser = userService.findByName(loggedInUsername);
        model.addAttribute("userDetails", currentUser);
        return "myprofile";
    }

    @GetMapping("profile/edit/{id}")
    public String myProfileEditForm(@PathVariable Long id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "myprofileEdit";
    }

    @PostMapping("profile/edit/{id}")
    public String myProfileEditSave(@PathVariable Long id, @ModelAttribute User updatedUser){
     userService.updateUser(id, updatedUser);
     return "redirect:/profile";
    }

}
