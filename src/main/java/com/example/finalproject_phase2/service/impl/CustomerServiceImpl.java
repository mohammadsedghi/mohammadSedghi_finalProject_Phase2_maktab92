package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.securityConfig.AuthenticationResponse;
import com.example.finalproject_phase2.securityConfig.CustomUserDetailsService;
import com.example.finalproject_phase2.securityConfig.JwtService;
import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.repository.CustomerRepository;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.service.WalletService;
import com.example.finalproject_phase2.mapper.CustomerMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.hash_password.EncryptPassword;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final WalletService walletService;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    EntityManager entityManager;
    CheckValidation checkValidation = new CheckValidation();

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, WalletService walletService, CustomerMapper customerMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.customerRepository = customerRepository;
        this.walletService = walletService;
        this.customerMapper = customerMapper;

        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return customerRepository.findByEmail(email);
        } catch (CustomException e) {
            throw new CustomException("transaction error");
        }
    }

    @Transactional
    @Override
    public CustomerDto addCustomer(CustomerDto customerDto) {
        try {
            if (!checkValidation.isValid(customerDto)) throw new CustomException("input is invalid ");
            findByEmail(customerDto.getEmail()).ifPresentOrElse(
                    tempCustomer -> {
                        throw new CustomException("customer with this email and password is exist ");

                    }, () -> {
                        Customer customer = customerMapper.customerDtoToCustomer(customerDto);
                        customer.setPassword(encryptCustomerPassword(customer.getPassword()));
                        customer.setRegisterDate(LocalDate.now());
                        customer.setRegisterTime(LocalTime.now());
                        customer.setWallet(walletService.createWallet());
                        customerRepository.save(customer);
                    });
            return customerDto;
        } catch (CustomException c) {
            return new CustomerDto();
        }
    }

    @Override
    public String encryptCustomerPassword(String password) {
        EncryptPassword encryptPassword = new EncryptPassword();
        return encryptPassword.hashPassword(password);
    }

    @Override
    public CustomerDto loginByEmailAndPassword(CustomerLoginDto customerLoginDto) {
        try {
            if (checkValidation.isValidEmail(customerLoginDto.getEmail()) && checkValidation.isValidPassword(customerLoginDto.getPassword())) {
                customerRepository.findByEmailAndPassword(customerLoginDto.getEmail(), encryptCustomerPassword(customerLoginDto.getPassword())).ifPresentOrElse(
                        customer -> {
                            CheckValidation.memberTypeCustomer = customer;
                        }
                        , () -> {
                            throw new CustomNoResultException("customer not found");
                        }
                );
            } else {
                throw new CustomNoResultException("you inter invalid input for login");
            }
        } catch (CustomNoResultException c) {
            CheckValidation.memberTypeCustomer = new Customer();
            return new CustomerDto();
        }
        return customerMapper.customerToCustomerDto(CheckValidation.memberTypeCustomer);
    }

//    @Override
    public boolean changePassword(CustomerChangePasswordDto customerChangePasswordDto) {
        try {
            if (checkValidation.isValidEmail(customerChangePasswordDto.getEmail()) && checkValidation.isValidPassword(customerChangePasswordDto.getOldPassword())) {
                if (checkValidation.isValidPassword(customerChangePasswordDto.getNewPassword())) {
                    customerRepository.findByEmailAndPassword(customerChangePasswordDto.getEmail(), encryptCustomerPassword(customerChangePasswordDto.getOldPassword())).ifPresentOrElse(
                            customer -> {
                                customer.setPassword(encryptCustomerPassword(customerChangePasswordDto.getNewPassword()));
                                customerRepository.save(customer);
                            }, () -> {
                                throw new CustomNoResultException("this customer is not found");
                            }
                    );
                } else {
                    throw new CustomNoResultException("new password is invalid");
                }
            } else {
                throw new CustomNoResultException("email and old password is invalid");
            }
        } catch (CustomNoResultException c) {
            return false;
        }
        return true;
    }

    public  Specification<Customer> hasCustomerWithThisEmail(String email) {
        return (customer, cq, cb) -> cb.equal(customer.get("email"), email);
    }

    public  Specification<Customer> hasCustomerWithThisFirstName(String firstName) {
        return (customer, cq, cb) -> cb.like(customer.get("firstName"), "%" + firstName + "%");
    }

    public  Specification<Customer> hasCustomerWithThisLastName(String lastName) {
        return (customer, cq, cb) -> cb.like(customer.get("lastName"), "%" + lastName + "%");
    }
    public  Specification<Customer> hasCustomerWithThisNationalId(String nationalId) {
        return (customer, cq, cb) -> cb.like(customer.get("nationalId"), "%" + nationalId + "%");
    }

    @Override
    public List<Customer> searchCustomer(CustomerDto customerDto) {
        Customer searchCustomer = customerMapper.customerDtoToCustomer(customerDto);
        return customerRepository.findAll(where(hasCustomerWithThisEmail(searchCustomer.getEmail())).
                or(hasCustomerWithThisFirstName(searchCustomer.getFirstName())).
                or(hasCustomerWithThisLastName(searchCustomer.getLastName()))
                .or(hasCustomerWithThisNationalId(searchCustomer.getNationalId()))
        );
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
//        Root<Customer> customer = query.from(Customer.class);
//        Predicate[] searchPredicate = new Predicate[3];
//        searchPredicate[0] = cb.equal(customer.get("firstName"), searchCustomer.getFirstName());
//        searchPredicate[1] = cb.equal(customer.get("lastName"), searchCustomer.getLastName());
//        searchPredicate[2] = cb.equal(customer.get("email"), searchCustomer.getEmail());
//        query.select(customer).where(searchPredicate);
//        return entityManager.createQuery(query).getResultList();
    }

    public AuthenticationResponse register(Customer customer){
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRegisterDate(LocalDate.now());
        customer.setRegisterTime(LocalTime.now());
        customer.setWallet(walletService.createWallet());
        customerRepository.save(customer);
        String jwtToken=jwtService.generateToken(customUserDetailsService.loadUserByUsername(customer.getEmail()));
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(CustomerLoginDto customerLoginDto){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customerLoginDto.getEmail(),customerLoginDto.getPassword()
                )
        );
        Customer customer=customerRepository.findByEmail(customerLoginDto.getEmail()).orElseThrow();
        String jwtToken=jwtService.generateToken(customUserDetailsService.loadUserByUsername(customer.getEmail()));
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    @Override
    public boolean changePassword(String email, String newPassword){
        Customer user = customerRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("user not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        customerRepository.save(user);
        return true;
    }
}