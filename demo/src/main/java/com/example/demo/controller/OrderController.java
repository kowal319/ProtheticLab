package com.example.demo.controller;

import com.example.demo.entity.OrderItem;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 1. Render the order management page with the form and the list of orders
    @GetMapping
    public String listOrders(Model model) {
        List<OrderItem> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("order", new OrderItem()); // empty order object for the form
        return "order-management"; // Thymeleaf template name
    }

    // 2. Create a new order from the form submission
    @PostMapping
    public String createOrder(@ModelAttribute OrderItem order) {
        orderService.createOrder(order);
        return "redirect:/orders"; // Redirect back to the order list after creating
    }


    // 3. Delete an order
    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders"; // Redirect back to the list after deletion
    }

    // 4. Get an order by id and populate the form for editing
    @GetMapping("/edit/{id}")
    public String editOrder(@PathVariable Long id, Model model) {
        Optional<OrderItem> orderItem = orderService.getOrderById(id);
        if (orderItem.isPresent()) {
            model.addAttribute("order", orderItem.get()); // populate the form with existing order data
            model.addAttribute("editMode", true); // flag to switch the form to edit mode
        } else {
            return "redirect:/orders"; // if order not found, redirect back
        }
        return "order-management";
    }

    // 5. Update the existing order after editing
    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute OrderItem updatedOrder) {
        orderService.updateOrder(id, updatedOrder);
        return "redirect:/orders"; // Redirect back to the order list after update
    }
}
