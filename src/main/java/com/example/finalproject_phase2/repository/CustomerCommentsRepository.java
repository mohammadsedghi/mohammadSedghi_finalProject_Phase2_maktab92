package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.CustomerComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCommentsRepository extends JpaRepository<CustomerComments,Long> {
}
