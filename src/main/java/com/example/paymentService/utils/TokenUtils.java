package com.example.paymentService.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

public class TokenUtils {

    public static UUID extractUserIdFromToken(String token) {
        String userInfoEndpoint = "https://auth.facoffee.hsborges.dev/realms/facoffee/protocol/openid-connect/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    userInfoEndpoint,
                    HttpMethod.GET,
                    entity,
                    Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> userInfo = response.getBody();
                if (userInfo != null && userInfo.containsKey("sub")) {
                    return UUID.fromString(userInfo.get("sub").toString());
                }
            }
        } catch (Exception e) {
            // Trate exceções de maneira apropriada, por exemplo, logando ou lançando uma exceção personalizada.
            e.printStackTrace();
        }

        

        return null;
    }
}
