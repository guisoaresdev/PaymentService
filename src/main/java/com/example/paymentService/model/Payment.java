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

    private boolean isSum;

    public Payment() {
    }

    public Payment(Long userId, BigDecimal amount, LocalDateTime timestamp, boolean isSum) {
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.isSum = isSum;
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

    public boolean getIsSum() {
        return this.isSum;
    }
    
    public void setIsSum(boolean isSum) {
        this.isSum = isSum;
    }
}
