package com.example.demo.service.Implementation;

import com.example.demo.entity.OrderItem;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

//    @Override
//    public OrderItem createOrder(OrderItem order) {
//        return orderRepository.save(order);
//    }

    @Override
    public OrderItem createOrder(OrderItem order) {
        // Get the current logged-in user from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Find the user by username (assuming username is unique in your system)
        User user = userService.findByName(currentUsername);

        // Set the user for the order
        order.setUser(user);

        // Save the order
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



    @Override
    public List<OrderItem> findOrdersByCurrentUser(Authentication authentication) {
        User currentUser = userService.getCurrentUser(authentication);

        if (hasRole(currentUser, "GABINET")) {
            return orderRepository.findByUser(currentUser);
        } else {
            return orderRepository.findAll();
        }
    }

    private boolean hasRole(User user, String roleName) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }


    @Override
    public OrderItem findById(Long id) {
        Optional<OrderItem> orderOptional = orderRepository.findById(id);
        return orderOptional.orElse(null);
    }
}
