package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
}
