package com.example.demo.controller;


import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public AdminController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/homeAdmin")
    public String shoHome(){
        return "admin/homeAdmin";
    }
}
