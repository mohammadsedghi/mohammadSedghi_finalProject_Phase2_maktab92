package com.example.finalproject_phase2.securityConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                   authorizeRequests.requestMatchers("api/admin/authentication").permitAll()
                           .requestMatchers("api/admin/register").permitAll()
                           .requestMatchers("api/customer/authenticationCustomer").permitAll()
                           .requestMatchers("api/customer/registerCustomer").permitAll()
                           .requestMatchers("api/specialist/signUp").permitAll()
                           .requestMatchers("api/specialist/login").permitAll()

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
                           .requestMatchers("api/customer/changeStatusOrderToStarted").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/changeStatusOrderToStarted").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/changeStatusOrderToDone").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusDone").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findOrdersInStatusPaid").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/submitCustomerComments").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findCustomerOrderSuggestionOnScore").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/findCustomerOrderSuggestionOnPrice").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/wallet/payment").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/wallet/send-data").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/wallet/after").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/wallet/endTime").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/wallet/captcha").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/wallet/payWithWallet").hasAuthority("CUSTOMER")
                           .requestMatchers("api/customer/email/send").hasAuthority("CUSTOMER")


                           .requestMatchers("api/specialist/confirmByAdmin").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/updateScore").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/showImage").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/showImage").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/search").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/showOrdersToSpecialist").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/showScore").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/submitSpecialSuggestion").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/findSuggestWithThisSpecialistAndOrder").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/changeSpecialistSelectedOfOrder").hasAuthority("SPECIALIST")
                           .requestMatchers("api/specialist/changeStatusOrderToWaitingForSpecialistToWorkplace").hasAuthority("SPECIALIST")


                           .requestMatchers("api/admin/duty/submit").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/duty/findAll").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/subDuty/addSubDuty").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/subDuty/editSubDutyPrice").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/subDuty/editSubDutyDescription").hasAuthority("ADMIN")
                           .requestMatchers("api/admin/subDuty/findAll").hasAuthority("ADMIN")
                           .requestMatchers("api/v1/demo-controller/say").hasAuthority("ADMIN").anyRequest().authenticated();

                   http.authenticationProvider(authenticationFilter)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

               });
        return http.build();
    }

}
