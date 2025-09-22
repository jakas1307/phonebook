package com.pb.phonebook.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pb.phonebook.entity.AppUser;
import com.pb.phonebook.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RegisterService {
    
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password, String role) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        appUserRepository.save(user);
    }
}
