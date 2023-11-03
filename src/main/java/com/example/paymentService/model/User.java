package com.example.paymentService.model;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal balance; // Saldo do usuário

    public Long getId() {
        return this.id;
    }

    // NAME
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // BALANCE
    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    } 
}
