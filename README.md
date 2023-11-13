# PaymentService
Um serviço de pagamento desenvolvido utilizando Java com Spring Boot e React TypeScript

## Project Structure
Front-end (UI): Implementado em TypeScript React, manipulando a interface do usuário e interagindo com os endpoints da API.

Back-end (API): Desenvolvido em Java com Spring Boot, gerenciando os endpoints para manipular usuários, pagamentos e histórico de pagamentos.

API Gateway: Spring Cloud Gateway do ecosistema Spring.

Persistência: Banco de dados H2, integrado com JPA (Java Persistence API).

## Running the project:
PaymentService/src/frontend/payment-service: npm start

PaymentService/src: ./gradlew build --refresh-dependencies

PaymentService/src: ./gradlew bootRun
