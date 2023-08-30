package com.example.finalproject_phase2.securityConfig;

import com.example.finalproject_phase2.service.AdminService;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AdminService adminService;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping("/say")
    public ResponseEntity<String> sayHello(){
        return  ResponseEntity.ok("hello");
    }
    @GetMapping("/way")
    public ResponseEntity<String> sayBy(){
        return  ResponseEntity.ok("by");
    }


}
