package com.example.paymentService;

import com.example.paymentService.repository.TransactionRepository;
import com.example.paymentService.model.Transaction;
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
        // MOCKUP USER
    }

    public void storeTransaction(UUID userId, Number amount, TransactionType type) {
        Transaction transaction = new Transaction(userId, amount, LocalDateTime.now(), type);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(UUID userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        // Calcular saldo
        Number balance = getUserBalance(userId);

        // Adicionar uma transação fictícia de saldo ao histórico
        Transaction saldoTransaction = new Transaction(userId, 3400, LocalDateTime.now(), TransactionType.SALDO);
        transactions.add(saldoTransaction);

        return transactions;
    }

    public Number getUserBalance(UUID userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        // Calcular saldo
        Number saldo = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.CREDIT) {
                saldo =+ (int) transaction.getAmount();
            } else if (transaction.getType() == TransactionType.DEBIT) {
                saldo =+ (int) transaction.getAmount();
            }
        }

        return saldo;
    }

    public void receivePayment(UUID userId, Number amount) {
        storeTransaction(userId, amount, TransactionType.CREDIT);
    }

    public void makePayment(UUID userId, Number amount) {
        storeTransaction(userId, amount, TransactionType.DEBIT);
    }
}
