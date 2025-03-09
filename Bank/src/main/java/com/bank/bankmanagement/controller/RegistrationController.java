package com.bank.bankmanagement.controller;

import com.bank.bankmanagement.model.User;
import com.bank.bankmanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        System.out.println("Открывается страница регистрации");
        model.addAttribute("user", new User());
        return "register";
    }


    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/login";
    }

}

