package com.example.paymentService;

import com.example.paymentService.repository.TransactionRepository;
import com.example.paymentService.model.Transaction;
import com.example.paymentService.model.TransactionStatus;
import com.example.paymentService.model.TransactionType;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void storeTransaction(UUID userId, Number amount, TransactionType type) {
        Transaction transaction = new Transaction(userId, amount, LocalDateTime.now(), type);
        transactionRepository.save(transaction);
    }

    public List<Transaction> transactionHistory(UUID userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        Number balance = getUserBalance(userId);

        Transaction saldoTransaction = new Transaction(userId, 3400, LocalDateTime.now(), TransactionType.SALDO, UUID.randomUUID());
        transactions.add(saldoTransaction);

        return transactions;
    }

    public Array<Number> UserBalance(UUID userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        
        Array<Number> balance;
        Number pendingTransactions = 0;
        Number currentBalance = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.CREDIT && transaction.getStatus() == TransactionStatus.PENDING) {
                balance[0].pendingTransactions =+ (int) transaction.getAmount();
            } else if (transaction.getType() == TransactionType.DEBIT) {
                balance[1].currentBalance =+ (int) transaction.getAmount();
            }
        }

        return balance;
    }

    public void credit(UUID userId, Number amount) {
        storeTransaction(userId, amount, TransactionType.CREDIT);
    }

    public void debit(UUID userId, Number amount) {
        storeTransaction(userId, amount, TransactionType.DEBIT);
    }
}
