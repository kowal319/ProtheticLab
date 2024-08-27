package com.example.demo;

import com.example.demo.entity.OrderItem;
import com.example.demo.service.Implementation.OrderServiceImpl;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProtheticLabApplication {

	@Autowired
	private OrderServiceImpl orderService;


	public static void main(String[] args) {
		SpringApplication.run(ProtheticLabApplication.class, args);

	}

}
