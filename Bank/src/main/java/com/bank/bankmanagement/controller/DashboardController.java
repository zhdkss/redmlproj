package com.bank.bankmanagement.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                            Model model) {
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        } else {
            model.addAttribute("username", "Guest");
        }
        return "dashboard"; // шаблон: dashboard.html
    }
}
