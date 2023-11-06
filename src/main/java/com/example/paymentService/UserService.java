package com.example.paymentService;

import com.example.paymentService.repository.PaymentRepository;
import com.example.paymentService.repository.UserRepository;
import com.example.paymentService.model.Payment;
import com.example.paymentService.model.User;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    public UserService(UserRepository userRepository, PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    // Cria um novo usuário
    public Long createUser(String name, BigDecimal initialBalance) {
        User newUser = new User(name, initialBalance);
        User savedUser = userRepository.save(newUser);
        return savedUser.getId();
    }

    // Armazena histórico de pagamentos
    public void storePaymentHistory(Long userId, BigDecimal amount, boolean isSum) {
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setTimestamp(LocalDateTime.now());
        payment.setIsSum(isSum);

        paymentRepository.save(payment);
    }

    public List<Payment> getPaymentHistory(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    // Encontra usuário pelo ID
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }

    // Receber Pagamento
    public void receivePayment(Long userId, BigDecimal amount, boolean isSum) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);

        storePaymentHistory(userId, amount, isSum);
    }

    // Faz Pagamento
    public void makePayment(Long userId, BigDecimal amount, boolean isSum) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.setBalance(user.getBalance().subtract(amount));
        userRepository.save(user);

        storePaymentHistory(userId, amount, isSum);
    }
    
}
