package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.WalletDto;
import com.example.finalproject_phase2.entity.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {
    public static Wallet walletDtoToWallet(WalletDto walletDto) {
        return Wallet.builder()
                .Balance(walletDto.getBalance())
                .build();
    }
}
