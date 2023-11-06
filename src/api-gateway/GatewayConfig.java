import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("receive-payment", r -> r
                .path("/receive-payment/**")
                .uri("http://localhost:8080"))
            .route("make-payment", r -> r
                .path("/make-payment/**")
                .uri("http://localhost:8080"))
            .route("create-user", r -> r
                .path("/create-user/**")
                .uri("http://localhost:8080"))
            .build();
    }
}

