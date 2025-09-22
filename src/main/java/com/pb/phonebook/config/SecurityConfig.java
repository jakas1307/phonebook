package com.pb.phonebook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.SavedRequest;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/home", "/login", "/css/**", "/js/**", "/images/**", "/register/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/users").permitAll()
            .requestMatchers("/users/new", "/users/edit/**", "/users/delete/**").hasRole("ADMIN")
            .requestMatchers("/companies/**", "/departments/**", "/locations/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    var savedRequest = (SavedRequest) request
                        .getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

                    if (savedRequest != null) {
                        response.sendRedirect(savedRequest.getRedirectUrl());
                    } else {
                        response.sendRedirect("/");
                    }
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) ->
                    response.sendRedirect("/login?unauthorized"))
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

