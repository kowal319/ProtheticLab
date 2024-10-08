package com.example.demo.service;

import com.example.demo.entity.OrderItem;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface OrderService {

        OrderItem createOrder(OrderItem order);
        Optional<OrderItem> getOrderById(Long id);
        List<OrderItem> getAllOrders();

        OrderItem updateOrder(Long id, OrderItem updatedOrder);
void deleteOrder(Long id);

    List<OrderItem> findOrdersByCurrentUser(Authentication authentication);

    OrderItem findById(Long id);
}


