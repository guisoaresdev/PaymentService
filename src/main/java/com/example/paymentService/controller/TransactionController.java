import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.paymentService.model.Transaction;
import com.example.paymentService.TransactionService;
import com.example.paymentService.utils.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    private UUID getAuthenticatedUserId(String authorizationHeader) {
        return TokenUtils.extractUserIdFromToken(authorizationHeader);
    }

    @Operation(summary = "Pega o histórico de transações do usuário")
    @RequestMapping(value = "/history", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Transaction>> getTransactionHistory(
            @RequestHeader(name = "Authorization") String authorizationHeader) {
        UUID authenticatedUserId = getAuthenticatedUserId(authorizationHeader);

        // Verifica se o usuário autenticado é o mesmo que está sendo solicitado
        if (isAuthenticatedUser(authenticatedUserId)) {
            List<Transaction> transactionHistory = transactionService.getTransactionHistory(authenticatedUserId);
            return ResponseEntity.ok(transactionHistory);
        } else {
            // Usuário não autorizado
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/{userId}/receive-payment/{amount}")
    @PreAuthorize("#userId == principal.username")
    public ResponseEntity<String> receivePayment(
            @PathVariable(value = "amount") Number amount, @PathVariable(value = "userId") UUID userId) {
        if (isAuthenticatedUser(userId)) {
            transactionService.receivePayment(userId, amount);
            return ResponseEntity.ok("Payment received successfully: " + amount);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/{userId}/make-payment/{amount}")
    @PreAuthorize("#userId == principal.username")
    public ResponseEntity<String> makePayment(
            @PathVariable(value = "amount") Number amount, @PathVariable(value = "userId") UUID userId) {
        if (isAuthenticatedUser(userId)) {
            transactionService.makePayment(userId, amount);
            return ResponseEntity.ok("Payment made successfully: " + amount);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/{userId}/balance")
    @PreAuthorize("#userId == principal.username")
    public ResponseEntity<Number> getUserBalance(@PathVariable(value = "userId") UUID userId) {
        if (isAuthenticatedUser(userId)) {
            Number balance = transactionService.getUserBalance(userId);
            return ResponseEntity.ok(balance);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
