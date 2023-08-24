package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.WalletDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionDto;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.Wallet;

public interface WalletService {
    Wallet createWallet();
    String payWithWallet(SpecialistSuggestionDto specialistSuggestionDto);
    String payWithOnlinePayment(Specialist specialist, Double proposedPrice );
}
