package com.example.paymentService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import java.lang.Number;

@Entity
@Table
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userId")
    private UUID userId;

    @Column(name = "amount")
    private Number amount;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private TransactionType type;

    public Transaction() {
    }

    public Transaction(UUID userId, Number amount, LocalDateTime timestamp, TransactionType type) {
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.type = type;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public Number getAmount() {
        return amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
