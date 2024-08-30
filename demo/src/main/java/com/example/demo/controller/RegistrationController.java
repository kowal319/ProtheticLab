package com.example.demo.controller;

import com.example.demo.dto.RegistrationDto;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("registrationDto") @Valid RegistrationDto registrationDto, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }

        userService.registerUser(registrationDto);
        return "redirect:/login?registrationSuccess";
    }


    @GetMapping("/login")
    public String shoHome(){
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        // Your login logic here
        return "redirect:/addOrder"; // Redirect to the products page after successful login
    }

}

