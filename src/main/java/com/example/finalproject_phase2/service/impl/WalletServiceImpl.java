package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionDto;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Specialist;
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


    public void withDraw(Customer customer, Specialist specialist,Double price) {
        Wallet customerWallet = customer.getWallet();
        Wallet specialistWallet=specialist.getWallet();
        Double customerBalance = customerWallet.getBalance();
        Double specialistBalance = specialistWallet.getBalance();
        Double calcCostOFOrder=(price*70)/100;
        customerBalance=customerBalance-price;
        specialistBalance=specialistBalance+calcCostOFOrder;
        customerWallet.setBalance(customerBalance);
        specialistWallet.setBalance(specialistBalance);
        walletRepository.save(customerWallet);
        walletRepository.save(specialistWallet);
    }

    @Override
    public String payWithWallet(SpecialistSuggestionDto specialistSuggestionDto) {
        try {
            withDraw(specialistSuggestionDto.getOrder().getCustomer(),
                    specialistSuggestionDto.getSpecialist(),
                    specialistSuggestionDto.getProposedPrice());
            return "transaction is success";
        }catch (CustomException ce){
            throw new CustomException("transaction is failed");
        }
    }

    @Override
    public String payWithOnlinePayment(SpecialistSuggestionDto specialistSuggestionDto) {
        return null;
    }
}
