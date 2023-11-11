package com.example.paymentService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private BigDecimal amount;

    private LocalDateTime timestamp;

    private boolean operation;

    public Payment() {
    }

    public Payment(Long userId, BigDecimal amount, LocalDateTime timestamp, boolean operation) {
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.operation = operation;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getOperation() {
        return this.operation;
    }
    
    public void setOperation(boolean isSum) {
        this.operation = operation;
    }
}
