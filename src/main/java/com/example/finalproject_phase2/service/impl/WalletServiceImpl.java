package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.entity.Wallet;
import com.example.finalproject_phase2.repository.WalletRepository;
import com.example.finalproject_phase2.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    public Wallet createWallet() {
        Wallet wallet = new Wallet(0d);
        walletRepository.save(wallet);
        return wallet;
    }
}
