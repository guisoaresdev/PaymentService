import com.example.paymentService.repository.TransactionRepository;
import com.example.paymentService.model.Transaction;
import com.example.paymentService.TransactionService;
import com.example.paymentService.PaymentServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = PaymentServiceApplication.class)
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testPaymentTransactions() {
        // Criando uma transação de crédito para um usuário fictício
        UUID userId = UUID.randomUUID(); // ID fictício de um usuário

        // Recebendo um pagamento de 1000
        Number paymentAmount = 1000;
        transactionService.receivePayment(userId, paymentAmount);
        // Obtendo o histórico de transações do usuário
        List<Transaction> transactionHistory = transactionRepository.findByUserId(userId);
        System.out.println("Adição de 1000: " + transactionHistory);
        // Verificando se a transação de crédito foi registrada corretamente
        assertNotNull(transactionHistory);

        // Realizando um pagamento de 500
        Number withdrawalAmount = 500;
        transactionService.makePayment(userId, withdrawalAmount);
        // Obtendo o histórico de transações atualizado do usuário
        List<Transaction> updatedTransactionHistory = transactionRepository.findByUserId(userId);
        System.out.println("Subtração de 500: " + updatedTransactionHistory);
    }
}
