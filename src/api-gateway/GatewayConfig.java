import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/receive-payment")
                        .uri("http://localhost:3000/receive-payment"))
                .route(p -> p.path("/make-payment")
                        .uri("http://localhost:3000/make-payment"))
                .route(p -> p.path("/create-user")
                        .uri("http://localhost:3000/create-user"))
                .build();
    }
}
