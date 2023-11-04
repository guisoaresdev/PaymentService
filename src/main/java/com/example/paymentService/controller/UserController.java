package com.example.paymentService.controller;

import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.paymentService.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/receive")
    public ResponseEntity<String> receivePayment(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        userService.receivePayment(userId, amount);
        return ResponseEntity.ok("Payment received successfully");
    }

    @PostMapping("/{userId}/debit")
    public ResponseEntity<String> makePayment(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        userService.makePayment(userId, amount);
        return ResponseEntity.ok("Payment made successfully");
    }

    // Outros endpoints conforme necessário para manipular o histórico, etc.
}
