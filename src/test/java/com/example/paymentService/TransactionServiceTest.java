import com.example.paymentService.PaymentServiceApplication;
import com.example.paymentService.repository.TransactionRepository;
import com.example.paymentService.model.Transaction;
import com.example.paymentService.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = PaymentServiceApplication.class)
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testPaymentTransactions() {
        // Criando uma transação de crédito para um usuário fictício
        UUID userId = UUID.randomUUID(); // ID fictício de um usuário

        // Recebendo um pagamento de 100
        BigDecimal paymentAmount = new BigDecimal("100.00");
        transactionService.receivePayment(userId, paymentAmount);

        // Obtendo o histórico de transações do usuário
        List<Transaction> transactionHistory = transactionRepository.findByUserId(userId);

        // Verificando se a transação de crédito foi registrada corretamente
        assertNotNull(transactionHistory);
        assertEquals(1, transactionHistory.size());
        assertEquals(paymentAmount, transactionHistory.get(0).getAmount());

        // Realizando um pagamento de 50
        BigDecimal withdrawalAmount = new BigDecimal("50.00");
        transactionService.makePayment(userId, withdrawalAmount);

        // Obtendo o histórico de transações atualizado do usuário
        List<Transaction> updatedTransactionHistory = transactionRepository.findByUserId(userId);

        // Verificando se a transação de débito foi registrada corretamente
        assertEquals(2, updatedTransactionHistory.size());
        assertEquals(withdrawalAmount, updatedTransactionHistory.get(1).getAmount());
    }
}
