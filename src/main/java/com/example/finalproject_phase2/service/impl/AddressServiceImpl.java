package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.AddressRepository;
import com.example.finalproject_phase2.service.AddressService;

public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
}
