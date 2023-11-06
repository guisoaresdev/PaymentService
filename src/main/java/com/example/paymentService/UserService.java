package com.example.paymentService;

import com.example.paymentService.repository.UserRepository;
import com.example.paymentService.model.User;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Cria um novo usuário
    public Long createUser(String name, BigDecimal initialBalance) {
        User newUser = new User(name, initialBalance);
        User savedUser = userRepository.save(newUser);
        return savedUser.getId();
    }

    // Receber Pagamento
    public void receivePayment(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);
    }

    // Faz Pagamento
    public void makePayment(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.setBalance(user.getBalance().subtract(amount));
        userRepository.save(user);
    }
    
}
