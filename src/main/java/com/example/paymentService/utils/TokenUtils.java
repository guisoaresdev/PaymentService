import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class TokenUtils {

    // Método para extrair o ID do usuário do token JWT emitido pelo Keycloak
    public static UUID extractUserIdFromToken(String authorizationHeader) {
        // Obtém apenas a parte do token JWT (ignora o prefixo "Bearer ")
        String token = authorizationHeader.replace("Bearer ", "");

        // Decodifica o token JWT
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedToken = new String(decodedBytes, StandardCharsets.UTF_8);

        // Parse do token JWT para obter as informações (claims)
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256))
                .build()
                .parseClaimsJws(decodedToken);

        // Obtém o ID do usuário do claim sub (subject)
        String userIdString = claimsJws.getBody().getSubject();
        return UUID.fromString(userIdString);
    }
}
