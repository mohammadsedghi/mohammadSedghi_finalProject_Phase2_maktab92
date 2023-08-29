package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomNumberFormatException;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDtoDescription;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.security_config.AuthenticationResponse;
import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.adminDto.AdminDto;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.entity.Admin;
import com.example.finalproject_phase2.service.AdminService;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.service.SubDutyService;
import com.example.finalproject_phase2.mapper.AdminMapper;
import com.example.finalproject_phase2.mapper.DutyMapper;
import com.example.finalproject_phase2.mapper.SubDutyMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private  final AdminService adminService;
    private final AdminMapper adminMapper;
    private final AuthenticationProvider authenticationProvider;
    private final DutyService dutyService;
    private final DutyMapper dutyMapper;
    private final SubDutyService subDutyService;
    private final SubDutyMapper subDutyMapper;

@Autowired
    public AdminController(AdminService adminService, AdminMapper adminMapper, AuthenticationProvider authenticationProvider, DutyService dutyService, DutyMapper dutyMapper, SubDutyService subDutyService, SubDutyMapper subDutyMapper) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
    this.authenticationProvider = authenticationProvider;

    this.dutyService = dutyService;
    this.dutyMapper = dutyMapper;
    this.subDutyService = subDutyService;
    this.subDutyMapper = subDutyMapper;
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
    @PostMapping("/submit")
    public ResponseEntity<DutyDto> addDuty(@RequestBody DutyDto dutyDto) {
        DutyDto dutyDtoGenerated = dutyService.addDuty(dutyDto);
        if (dutyDtoGenerated!=null)return new ResponseEntity<>(dutyDtoGenerated, HttpStatus.ACCEPTED);
        else throw new CustomException("duty not saved");
    }
    @GetMapping("/findAll")
    public Set<DutyDto> getAllDuty() {
        return dutyService.findAllByDuties();
    }
    @PostMapping("/addSubDuty")
    public ResponseEntity<SubDutyDto> addSubDuty(@RequestBody SubDutyDto subDutyDto) {
        SubDutyDto subDutyDtoCandidate = subDutyService.addSubDuty(subDutyDto);
        if (subDutyDto!=null)return new ResponseEntity<>(subDutyDtoCandidate, HttpStatus.ACCEPTED);
        else throw  new CustomException("subDuty not saved");
    }
    @PostMapping("/editSubDutyPrice")
    public ResponseEntity<SubDutyDto> editSubDutyPrice(@RequestBody EditSubDutyDto editSubDutyDto) {
        SubDutyDto subDutyDto = subDutyService.editSubDutyPrice(editSubDutyDto);
        if (subDutyDto!=null)return new ResponseEntity<>(subDutyDto, HttpStatus.ACCEPTED);
        else throw  new CustomNumberFormatException("invalid price");
    }
    @PostMapping("/editSubDutyDescription")
    public ResponseEntity<SubDutyDto> editSubDutyDescription(@RequestBody EditSubDutyDtoDescription editSubDutyDtoDescription) {
        SubDutyDto subDutyDto = subDutyService.editSubDutyDescription(editSubDutyDtoDescription);
        if (subDutyDto!=null)return new ResponseEntity<>(subDutyDto, HttpStatus.ACCEPTED);
        else throw  new CustomNumberFormatException("invalid price");
    }
    @GetMapping("/findAll")
    public Set<SubDutyDto> getAllSubDuty(DutyDto dutyDto) {
        return subDutyMapper.collectionOfSubDutyToSetOfSubDutyDto(subDutyService.showAllSubDutyOfDuty(dutyDto));
    }
}
