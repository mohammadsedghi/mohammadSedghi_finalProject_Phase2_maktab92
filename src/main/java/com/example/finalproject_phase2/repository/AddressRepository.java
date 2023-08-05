package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
