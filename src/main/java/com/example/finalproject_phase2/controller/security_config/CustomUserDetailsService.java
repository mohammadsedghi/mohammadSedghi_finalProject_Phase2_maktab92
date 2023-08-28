package com.example.finalproject_phase2.controller.security_config;

import com.example.finalproject_phase2.entity.Admin;
import com.example.finalproject_phase2.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<Admin> userOptional = adminRepository.findByEmail(username.trim());
        if (userOptional.isPresent()) {
            return new AdminUserDetail(
                    userOptional.get()
            );
        }
        throw new UsernameNotFoundException(username + " not found");
    }
}
