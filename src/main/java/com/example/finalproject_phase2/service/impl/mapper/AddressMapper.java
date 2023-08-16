package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.entity.Address;

public interface AddressMapper {
     Address addressDtoToAddress(AddressDto addressDto);
      AddressDto addressToAddressDto(Address address);


}
