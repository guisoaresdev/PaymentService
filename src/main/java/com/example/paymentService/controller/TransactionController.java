package com.example.paymentService.controller;
import com.example.paymentService.model.Transaction;
import com.example.paymentService.TransactionService;
import com.example.paymentService.utils.TokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable UUID userId) {
        List<Transaction> transactionHistory = transactionService.getTransactionHistory(userId);
        return ResponseEntity.ok(transactionHistory);
    }

    @PostMapping("/receive-payment")
    public ResponseEntity<String> receivePayment(@RequestBody Map<String, BigDecimal> requestBody, @RequestHeader("Authorization") String authorizationHeader) {
        // Validar o token
        UUID userId = TokenUtils.extractUserIdFromToken(authorizationHeader);
        BigDecimal amount = requestBody.get("amount");
        transactionService.receivePayment(userId, amount);
        return ResponseEntity.ok("Payment received successfully");
    }

    @PostMapping("/make-payment")
    public ResponseEntity<String> makePayment(@RequestBody Map<String, BigDecimal> requestBody, @RequestHeader("Authorization") String authorizationHeader) {
        // Validar o token
        UUID userId = TokenUtils.extractUserIdFromToken(authorizationHeader);
        BigDecimal amount = requestBody.get("amount");
        transactionService.makePayment(userId, amount);
        return ResponseEntity.ok("Payment made successfully");
    }
}
