package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionDto;
import com.example.finalproject_phase2.security_config.AuthenticationResponse;
import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.dto.customerCommentsDto.CustomerCommentsDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithCustomerAndSubDuty;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithOrdersStatus;
import com.example.finalproject_phase2.dto.ordersDto.OrdersWithPriceAndBasePrice;
import com.example.finalproject_phase2.entity.Address;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.service.*;
import com.example.finalproject_phase2.mapper.AddressMapper;
import com.example.finalproject_phase2.mapper.CustomerMapper;
import com.example.finalproject_phase2.mapper.OrdersMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final AddressService addressService;
    private final CustomerCommentsService customerCommentsService;
    private final OrdersService ordersService;
    private final SpecialistSuggestionService specialistSuggestionService;
    private final AddressMapper addressMapper;
    private final CustomerMapper customerMapper;
    private final OrdersMapper ordersMapper;


    @Autowired
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper, AddressService addressService, CustomerCommentsService customerCommentsService, AddressMapper addressMapper, OrdersService ordersService, SpecialistSuggestionService specialistSuggestionService, OrdersMapper ordersMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.addressService = addressService;
        this.customerCommentsService = customerCommentsService;
        this.addressMapper = addressMapper;
        this.ordersService = ordersService;
        this.specialistSuggestionService = specialistSuggestionService;
        this.ordersMapper = ordersMapper;
    }

    @PostMapping("/signUp")
    public ResponseEntity<CustomerDto> AddCustomer(@RequestBody @Valid CustomerDto customerDto) {
        CustomerDto customerDtoResult = customerService.addCustomer(customerDto);
        if (customerDtoResult != null) {
            return new ResponseEntity<>(customerDtoResult, HttpStatus.ACCEPTED);
        } else throw new CustomException("customer not saved");
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerDto> loginByEmailAndPassword(@RequestBody @Valid CustomerLoginDto customerLoginDto) {
        CustomerDto customerDto = customerService.loginByEmailAndPassword(customerLoginDto);
        if (customerDto != null) {
            return new ResponseEntity<>(customerDto, HttpStatus.ACCEPTED);
        } else throw new CustomException("customer not saved");
    }

    //    @PostMapping("/changePassword")
//    public ResponseEntity<Boolean> changePassword(@RequestBody @Valid  CustomerChangePasswordDto customerChangePasswordDto) {
//       if(customerService.changePassword(customerChangePasswordDto)) {
//           return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
//       }else throw new CustomNoResultException("password not changed");
//    }
    @PostMapping("/search")
    public ResponseEntity<List<Customer>> searchSpecialist(@RequestBody CustomerDto customerDto) {
        List<Customer> customers = customerService.searchCustomer(customerDto);
        return new ResponseEntity<>(customers, HttpStatus.ACCEPTED);
    }

    @PostMapping("/registerCustomer")
    public ResponseEntity<AuthenticationResponse> registerCustomer(@RequestBody CustomerDto customerDto
            , @RequestParam String userType) {
        System.out.println(customerMapper.customerDtoToCustomer(customerDto).getEmail());
        CheckValidation.userType = userType;
        System.out.println(userType);
        if (userType.equals("customer")) {
            return ResponseEntity.ok(customerService.register(customerMapper.customerDtoToCustomer(customerDto)));
        } else return new ResponseEntity<>(new AuthenticationResponse(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/authenticationCustomer")
    public ResponseEntity<AuthenticationResponse> loginCustomer(@RequestBody CustomerLoginDto customerLoginDto
            , @RequestParam String userType) {
        CheckValidation.userType = userType;
        System.out.println(customerLoginDto.getEmail());
        System.out.println(userType);
        if (userType.equals("customer")) {
            return ResponseEntity.ok(customerService.authenticate(customerLoginDto));
        } else return new ResponseEntity<>(new AuthenticationResponse(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody @Valid CustomerChangePasswordDto customerChangePasswordDto) {

        if (customerService.changePassword(customerChangePasswordDto.getEmail(), customerChangePasswordDto.getNewPassword())) {
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else throw new CustomNoResultException("password not changed");
    }

    @PostMapping("/deleteAddress")
    public ResponseEntity<AddressDto> deleteAddress(@RequestBody AddressDto addressDto) {

        AddressDto address = addressService.removeAddress(addressDto);

        if (address != null) return new ResponseEntity<>(address, HttpStatus.ACCEPTED);
        else
            throw new CustomException("address not found");
    }

    @PostMapping("/submitAddress")
    public ResponseEntity<AddressDto> submitAddress(@RequestBody AddressDto addressDto) {
        Address address = addressService.createAddress(addressDto);
        if (address != null)
            return new ResponseEntity<>(addressMapper.addressToAddressDto(address), HttpStatus.ACCEPTED);
        else
            throw new CustomException("address not saved");
    }
    @PostMapping("/submitOrders")
    public ResponseEntity<OrdersDto> submitOrders(@RequestBody @Valid OrdersWithPriceAndBasePrice ordersWithPriceAndBasePrice ) {
        OrdersDto ordersDto = ordersService.submitOrder(ordersWithPriceAndBasePrice);
        if (ordersDto!=null){
            return new ResponseEntity<>(ordersDto, HttpStatus.ACCEPTED);
        }else  throw new CustomException("orders not saved");
    }
    @PostMapping("/findOrdersWithThisCustomerAndSubDuty")
    public ResponseEntity<OrdersDto> findOrdersWithThisCustomerAndSubDuty(@RequestBody @Valid OrdersDtoWithCustomerAndSubDuty ordersDtoWithCustomerAndSubDuty ) {
        OrdersDto ordersDto = ordersService.findOrdersWithThisCustomerAndSubDuty(ordersDtoWithCustomerAndSubDuty);
        if (ordersDto!=null){
            return new ResponseEntity<>(ordersDto, HttpStatus.ACCEPTED);
        }else throw new CustomException("orders not found");
    }
    @PostMapping("/updateOrderToNextLevelStatus")
    public ResponseEntity<OrdersDto> updateOrderToNextLevelStatus(@RequestBody @Valid OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus ) {
        OrdersDto ordersDto = ordersMapper.ordersToOrdersDto(ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus));
        return new ResponseEntity<>(ordersDto, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusWaitingForSpecialistSuggestion")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusWaitingForSpecialistSuggestion(@RequestBody @Valid CustomerDto customerDto ) {
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusWaitingForSpecialistSuggestion(customerDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusWaitingForSpecialistSelection")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusWaitingForSpecialistSelection(@RequestBody @Valid CustomerDto customerDto ) {
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusWaitingForSpecialistSelection(customerDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusWaitingForSpecialistToWorkplace")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusWaitingForSpecialistToWorkplace(@RequestBody @Valid CustomerDto customerDto ) {
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusWaitingForSpecialistToWorkplace(customerDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusStarted")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusStarted(@RequestBody @Valid CustomerDto customerDto ) {
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusStarted(customerDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusDone")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusDone(@RequestBody @Valid CustomerDto customerDto ) {
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusDone(customerDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusPaid")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusPaid(@RequestBody @Valid CustomerDto customerDto ) {
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusPaid(customerDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/submitCustomerComments")
    public ResponseEntity<Boolean> submitCustomerComments(@RequestBody @Valid CustomerCommentsDto customerCommentsDto) {
        customerCommentsService.submitCustomerCommentsService(customerCommentsDto);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findCustomerOrderSuggestionOnScore")
    public ResponseEntity<List<SpecialistSuggestionDto>> findCustomerOrderSuggestionOnScoreOfSpecialist(@RequestBody @Valid CustomerDto customerDto) {
        List<SpecialistSuggestionDto> customerOrderSuggestionOnScoreOfSpecialist = specialistSuggestionService.findCustomerOrderSuggestionOnScoreOfSpecialist(customerDto);
        return new ResponseEntity<>(customerOrderSuggestionOnScoreOfSpecialist, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findCustomerOrderSuggestionOnPrice")
    public ResponseEntity<List<SpecialistSuggestionDto>> findCustomerOrderSuggestionOnPrice(@RequestBody @Valid CustomerDto CustomerDto ) {
        List<SpecialistSuggestionDto> customerOrderSuggestionOnPrice = specialistSuggestionService.findCustomerOrderSuggestionOnPrice(CustomerDto);
        return new ResponseEntity<>(customerOrderSuggestionOnPrice, HttpStatus.ACCEPTED);
    }
}