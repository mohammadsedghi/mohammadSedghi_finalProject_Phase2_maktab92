package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
