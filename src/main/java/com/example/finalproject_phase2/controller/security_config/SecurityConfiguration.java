package com.example.finalproject_phase2.controller.security_config;

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
                   authorizeRequests.requestMatchers("/api/v1/auth/authentication").permitAll()
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
