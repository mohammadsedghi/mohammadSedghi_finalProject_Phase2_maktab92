package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.entity.Address;
import com.example.finalproject_phase2.repository.AddressRepository;
import com.example.finalproject_phase2.service.AddressService;
import com.example.finalproject_phase2.service.impl.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address createAddress(AddressDto addressDto) {
        return addressRepository.save(AddressMapper.addressDtoToAddress(addressDto));
    }

    @Override
    public ProjectResponse removeAddress(Address address) {
        try {
            addressRepository.findAddressById(address).ifPresentOrElse(
                    addressCandidate -> {
                        addressRepository.delete(address);
                    }
                    , () -> {
                        throw new CustomNoResultException("no any address found");
                    }
            );
        }catch (CustomNoResultException cnr){
            return new ProjectResponse("500",cnr.getMessage());
        }
        return new ProjectResponse("202","address removed");
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id).get();
    }

}
