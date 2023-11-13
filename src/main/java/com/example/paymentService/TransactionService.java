package com.example.paymentService;

import com.example.paymentService.repository.TransactionRepository;
import com.example.paymentService.model.Transaction;
import com.example.paymentService.model.TransactionType;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void storeTransaction(UUID userId, BigDecimal amount, TransactionType type) {
        Transaction transaction = new Transaction(userId, amount, LocalDateTime.now(), type);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(UUID userId) {
        return transactionRepository.findByUserId(userId);
    }

    public void receivePayment(UUID userId, BigDecimal amount) {
        storeTransaction(userId, amount, TransactionType.CREDIT);
    }

    public void makePayment(UUID userId, BigDecimal amount) {
        storeTransaction(userId, amount, TransactionType.DEBIT);
    }
}
