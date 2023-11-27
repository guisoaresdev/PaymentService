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

    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Pega o histórico de transações do usuário", description="Utiliza do Bearer para validar o token")
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

    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Recebe uma transação assinada pelo user")
    @PostMapping("/credit/{amount}")
    public ResponseEntity<String> Credit(
        @RequestHeader(name = "Authorization") String sender, @PathVariable(value = "amount") Number amount) {
        if (isAuthenticatedUser(userId)) {
            transactionService.credit(sender, amount);
            return ResponseEntity.ok("Payment received successfully: " + amount);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Realiza uma transação assinada pelo user")
    @PostMapping("/debit/{amount}")
    public ResponseEntity<String> Debit(
          @RequestHeader(name = "Authorization") UUID sender, @PathVariable(value = "amount") Number amount) {
        if (isAuthenticatedUser(sender)) {
            transactionService.debit(sender, amount);
            return ResponseEntity.ok("Payment made successfully: " + amount);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Resgata o balanço do usuário")
    @GetMapping("/{userId}/balance")
    public ResponseEntity<Number> UserBalance(@RequestHeader(name = "Authorization") UUID sender) {
        if (isAuthenticatedUser(userId)) {
            Number balance = transactionService.UserBalance(userId);
            return ResponseEntity.ok(balance);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
