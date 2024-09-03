package com.example.demo.controller;

import com.example.demo.entity.OrderItem;
import com.example.demo.entity.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // 1. Render the order management page with the form and the list of orders
    @GetMapping("orders")
    public String listOrders(Model model) {
        List<OrderItem> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders-list"; // Thymeleaf template name
    }

    @GetMapping("/addOrder")
            public String addOrderForm(Model model) {
        model.addAttribute("order", new OrderItem()); // empty order object for the form
        return "add-order";
    }


    // 2. Create a new order from the form submission
    @PostMapping("/addOrder")
    public String createOrder(@ModelAttribute OrderItem order, RedirectAttributes redirectAttributes) {
        orderService.createOrder(order);
        redirectAttributes.addFlashAttribute("successMessage", "Zamówienie złożone !");
        return "redirect:/addOrder"; // Redirect back to the order list after creating
    }

    @GetMapping("/profile/addOrder")
    public String showOrderForm(Model model, Authentication authentication) {
        // Get the currently logged-in user
        User currentUser = userService.getCurrentUser(authentication);

        // Create a new Order object
        OrderItem order = new OrderItem();

        // Prepopulate order fields with data from the current user's profile
        order.setName(currentUser.getName());
        order.setOpeningHours(currentUser.getOpeningHours());
        // You can prepopulate other fields if necessary

        // Add the order to the model
        model.addAttribute("order", order);

        return "add-order-logged"; // name of your Thymeleaf template (orderForm.html)
    }

    @PostMapping("/profile/addOrder")
    public String submitOrder(@ModelAttribute OrderItem order, RedirectAttributes redirectAttributes) {
        // Save the order
        orderService.createOrder(order);
        redirectAttributes.addFlashAttribute("successMessage", "Zamówienie złożone !");

        return "redirect:/orders"; // Redirect to the same form or elsewhere
    }


    // 3. Delete an order
    @PostMapping("orders/delete/{id}")
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
