package com.yourname.bookstore.controller;

import com.yourname.bookstore.entity.User;
import com.yourname.bookstore.repository.UserRepository;
import com.yourname.bookstore.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @PostMapping("/purchase")
    public String purchaseBook(@RequestParam Long bookId, @RequestParam Integer quantity, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        orderService.placeOrder(user, bookId, quantity);

        return "redirect:/?success";
    }
}
