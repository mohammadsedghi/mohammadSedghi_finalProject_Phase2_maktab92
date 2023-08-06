package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.WalletRepository;
import com.example.finalproject_phase2.service.WalletService;

public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }
}
