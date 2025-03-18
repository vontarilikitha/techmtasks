package com.ticketbooking.config;

//... existing imports ...
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http
         .csrf(csrf -> csrf.disable())
         .cors(cors -> cors.configurationSource(corsConfigurationSource()))
         .authorizeHttpRequests(auth -> auth
             .requestMatchers("/api/events/**").permitAll()
             .requestMatchers("/api/bookings/**").permitAll()
             .requestMatchers("/api/auth/**").permitAll()
             .requestMatchers("/api/users/**").permitAll()
             // Add any other public endpoints
             .anyRequest().authenticated()
         )
         .sessionManagement(session -> session
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         );
         
     return http.build();
 }
 
 @Bean
 public CorsConfigurationSource corsConfigurationSource() {
     CorsConfiguration configuration = new CorsConfiguration();
     configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
     configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
     configuration.setAllowedHeaders(Arrays.asList("*"));
     configuration.setAllowCredentials(true);
     
     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
     source.registerCorsConfiguration("/**", configuration);
     return source;
 }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}