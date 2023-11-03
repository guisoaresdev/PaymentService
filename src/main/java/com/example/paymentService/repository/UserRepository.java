package com.example.paymentService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.paymentService.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // É uma interface vazia que herda os métodos do JpaRepository que são relacionados a operações de persistência basica
    // Contém métodos para operações CRUD como save, findById, findAll, delete
}
