package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
}
