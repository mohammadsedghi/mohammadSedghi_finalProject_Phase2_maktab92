package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.controller.security_config.AuthenticationResponse;
import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.adminDto.AdminDto;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.entity.Admin;
import com.example.finalproject_phase2.service.AdminService;
import com.example.finalproject_phase2.service.impl.mapper.AdminMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private  final AdminService adminService;
    private final AdminMapper adminMapper;
    private final AuthenticationProvider authenticationProvider;

@Autowired
    public AdminController(AdminService adminService, AdminMapper adminMapper, AuthenticationProvider authenticationProvider) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
    this.authenticationProvider = authenticationProvider;

}

    @PostMapping("/login2")
    public ResponseEntity<AdminDto> loginByEmailAndPassword(@RequestBody @Valid AdminLoginDto adminLoginDto) {
        AdminDto adminDto = adminService.loginByEmailAndPassword(adminLoginDto);
        if (adminDto!=null){
            return new ResponseEntity<>(adminDto, HttpStatus.ACCEPTED);
        }else  throw new CustomException("customer not saved");
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Admin admin){
        System.out.println(admin.getEmail());
        return  ResponseEntity.ok(adminService.register(admin));
    }
    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AdminLoginDto adminLoginDto
            , @RequestParam String userType){
        CheckValidation.userType=userType;
        System.out.println(userType);
        if (userType.equals("admin")){
            return  ResponseEntity.ok(adminService.authenticate(adminLoginDto));
        }else  return new ResponseEntity<>(new AuthenticationResponse(), HttpStatus.BAD_REQUEST);
    }
}
