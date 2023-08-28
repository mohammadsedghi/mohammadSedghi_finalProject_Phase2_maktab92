package com.example.finalproject_phase2.controller.security_config;

import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.entity.Admin;
import com.example.finalproject_phase2.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AdminService adminService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Admin admin){
        System.out.println(admin.getEmail());
      return  ResponseEntity.ok(adminService.register(admin));
    }
    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AdminLoginDto adminLoginDto){
        return  ResponseEntity.ok(adminService.authenticate(adminLoginDto));
    }
    @GetMapping("/say")
    public ResponseEntity<String> sayHello(){
        return  ResponseEntity.ok("hello");
    }
    @GetMapping("/way")
    public ResponseEntity<String> sayBy(){
        return  ResponseEntity.ok("by");
    }

}
