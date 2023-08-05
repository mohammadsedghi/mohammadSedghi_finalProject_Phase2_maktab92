package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByEmailAndPassword(String email,String password);
}
