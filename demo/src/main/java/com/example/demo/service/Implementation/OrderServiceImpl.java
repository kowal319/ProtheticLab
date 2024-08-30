package com.example.demo.service.Implementation;

import com.example.demo.entity.OrderItem;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderItem createOrder(OrderItem order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<OrderItem> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<OrderItem> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderItem updateOrder(Long id, OrderItem updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setDescription(updatedOrder.getDescription());
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
