/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author Huy
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Cloudinary getCloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dn0kj5rfm",
                "api_key", "636627396695933",
                "api_secret", "NTSuTm07I86Aj1cMGaZW2cPDMg8",
                "secure", true));
        return cloudinary;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for simplicity, enable for production use
                .authorizeHttpRequests(authz -> authz
                //                .requestMatchers("/categories/**").permitAll()
                //                .requestMatchers("/discounts/**").permitAll()
                //                .requestMatchers("/books/**").permitAll()
                //                .requestMatchers("/publishers/**").permitAll()
                //                .requestMatchers("/users/**").permitAll()
                //                .requestMatchers("/roles/**").permitAll()
                //                .requestMatchers("/", "/login", "/users/create", "/**").permitAll() // Public access to login and register
                //                .anyRequest().authenticated() // Require authentication for all other requests

                .requestMatchers("/books/**").hasAnyRole("ADMIN", "EMPLOYEE") // Only Admin and Employee can access /books
                .requestMatchers("/publishers/**").hasAnyRole("ADMIN", "EMPLOYEE") // Only Admin and Employee can access /publishers
                .requestMatchers("/discounts/**").hasAnyRole("ADMIN", "EMPLOYEE") // Only Admin and Employee can access /discounts
                .requestMatchers("/categories/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers("/receipts/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers("/authors/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers("/", "/login", "/users/create", "/cart", "/cart/**", // Public access to certain routes
                        "/books", "/books?page=**",  // Allow access to all book pages
                        "/?name=*&namePublisher=*&fullNameAuthor=*&nameCategory=*&minPrice=*&maxPrice=*").permitAll() // Allow any values for these parameters
                .requestMatchers("/**", "/users/**").hasRole("ADMIN") // Admin can access everything else
                .anyRequest().authenticated() // Require authentication for all other requests
                ).formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(successHandler) // Sử dụng CustomAuthenticationSuccessHandler
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                )
                .sessionManagement(session -> session
                .sessionFixation().migrateSession() // Đảm bảo session không bị mất sau khi đăng nhập
                );

        return http.build();
    }
}
