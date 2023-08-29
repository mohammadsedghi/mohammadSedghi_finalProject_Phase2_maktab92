package com.example.finalproject_phase2.controller.security_config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
   private final JwtAuthenticationFilter  jwtAuthFilter;
    private final AuthenticationProvider authenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http.csrf(AbstractHttpConfigurer::disable);
//        http.cors(AbstractHttpConfigurer::disable);
         http.authorizeHttpRequests(
               authorizeRequests -> {
                   authorizeRequests.requestMatchers("api/v1/auth/authentication").permitAll()
                           .requestMatchers("api/customer/authenticationCustomer").permitAll()
                           .requestMatchers("api/customer/registerCustomer").permitAll()
                           .requestMatchers("api/v1/auth/way").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/changePassword").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/deleteAddress").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/submitAddress").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/submitOrders").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersWithThisCustomerAndSubDuty").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/updateOrderToNextLevelStatus").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusWaitingForSpecialistSuggestion").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusWaitingForSpecialistSelection").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusWaitingForSpecialistToWorkplace").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusStarted").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusDone").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusPaid").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/submitCustomerComments").hasAuthority("CUSTOMER")
                           .requestMatchers("/api/v1/demo-controller/say")
                           .hasAuthority("ADMIN").anyRequest().authenticated();

                   http.authenticationProvider(authenticationFilter)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

               });
//       ).httpBasic(basic -> {
//       });
        return http.build();
    }

}
