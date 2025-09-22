package com.pb.phonebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pb.phonebook.entity.AppUser;
import com.pb.phonebook.service.RegisterService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") AppUser user) {
        registerService.registerUser(user.getUsername(), user.getPassword(), user.getRole());
        return "redirect:/login?registered";
    }
}
