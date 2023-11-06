package com.example.paymentService.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.paymentService.UserService;
import com.example.paymentService.model.Payment;
import com.example.paymentService.model.User;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<Long> createUser(@RequestBody User user) {
        Long userId = userService.createUser(user.getName(), user.getBalance());
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/{userId}/payment-history")
    public ResponseEntity<List<Payment>> getPaymentHistory(@PathVariable Long userId) {
        List<Payment> paymentHistory = userService.getPaymentHistory(userId);
        return ResponseEntity.ok(paymentHistory);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{userId}/receive-payment")
    public ResponseEntity<String> receivePayment(@PathVariable Long userId, @RequestBody Map<String, BigDecimal> requestBody) {
        BigDecimal amount = requestBody.get("amount");
        userService.receivePayment(userId, amount, true);
        return ResponseEntity.ok("Payment received successfully");
    }

    @PostMapping("/{userId}/make-payment")
    public ResponseEntity<String> makePayment(@PathVariable Long userId, @RequestBody Map<String, BigDecimal> requestBody) {
        BigDecimal amount = requestBody.get("amount");
        userService.makePayment(userId, amount, false);
        return ResponseEntity.ok("Payment made successfully");
    }
}
