package com.example.finalproject_phase2.controller.security_config;

import com.example.finalproject_phase2.entity.Admin;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Getter
public class AdminUserDetail implements UserDetails {
    private final Admin admin;

    public AdminUserDetail(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ADMIN"));
        //List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        if (person instanceof Customer) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
//        } else if (person instanceof Admin) {
        //  authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        } else if (person instanceof Specialist) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_SPECIALIST"));
//        }
        // return authorities;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
