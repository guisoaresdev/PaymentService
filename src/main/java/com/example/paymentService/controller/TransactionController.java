import com.example.paymentService.model.Transaction;
import com.example.paymentService.TransactionService;
import com.example.paymentService.utils.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Get transaction history for a user", description = "Retrieve the transaction history for a user.")
    @GetMapping("/{userId}/history")
    public ResponseEntity<List<Transaction>> getTransactionHistory(
            @Parameter(description = "User ID", required = true)
            @PathVariable UUID userId) {
        List<Transaction> transactionHistory = transactionService.getTransactionHistory(userId);
        return ResponseEntity.ok(transactionHistory);
    }

    @Operation(summary = "Receive payment", description = "Receive a payment for a user.")
    @PostMapping("/receive-payment")
    public ResponseEntity<String> receivePayment(
            @RequestBody Map<String, BigDecimal> requestBody,
            @RequestHeader("Authorization") String authorizationHeader) {
        // Validar o token
        UUID userId = TokenUtils.extractUserIdFromToken(authorizationHeader);
        BigDecimal amount = requestBody.get("amount");
        transactionService.receivePayment(userId, amount);
        return ResponseEntity.ok("Payment received successfully");
    }

    @Operation(summary = "Make payment", description = "Make a payment for a user.")
    @PostMapping("/make-payment")
    public ResponseEntity<String> makePayment(
            @RequestBody Map<String, BigDecimal> requestBody,
            @RequestHeader("Authorization") String authorizationHeader) {
        // Validar o token
        UUID userId = TokenUtils.extractUserIdFromToken(authorizationHeader);
        BigDecimal amount = requestBody.get("amount");
        transactionService.makePayment(userId, amount);
        return ResponseEntity.ok("Payment made successfully");
    }
}
