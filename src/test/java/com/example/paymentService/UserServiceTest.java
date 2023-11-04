import com.example.paymentService.PaymentServiceApplication;
import com.example.paymentService.repository.UserRepository;
import com.example.paymentService.UserService;
import com.example.paymentService.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = PaymentServiceApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testPaymentTransactions() {
        // Criando um novo usuário
        User user = new User();
        user.setName("Alice"); // Nome aleatório
        user.setBalance(new BigDecimal("500.00")); // Saldo inicial

        // Salvando o usuário no banco de dados
        User savedUser = userRepository.save(user);

        // Garantindo que o usuário foi salvo corretamente
        assertNotNull(savedUser.getId(), "O ID do usuário salvo não deveria ser nulo");

        // Recebendo um pagamento de 100
        BigDecimal paymentAmount = new BigDecimal("100.00");
        userService.receivePayment(savedUser.getId(), paymentAmount);

        // Buscando o usuário após receber o pagamento
        User userAfterReceive = userRepository.findById(savedUser.getId()).orElse(null);

        // Verificando se o saldo foi atualizado corretamente após o recebimento do pagamento
        assertEquals(new BigDecimal("600.00"), userAfterReceive.getBalance());

        // Realizando um pagamento de 50
        BigDecimal withdrawalAmount = new BigDecimal("50.00");
        userService.makePayment(savedUser.getId(), withdrawalAmount);

        // Buscando o usuário após fazer o pagamento
        User userAfterPayment = userRepository.findById(savedUser.getId()).orElse(null);

        // Verificando se o saldo foi atualizado corretamente após o pagamento
        assertEquals(new BigDecimal("550.00"), userAfterPayment.getBalance());
    }
}
