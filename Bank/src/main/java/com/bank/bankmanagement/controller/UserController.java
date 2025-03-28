package com.bank.bankmanagement.controller;

import com.bank.bankmanagement.model.User;
import com.bank.bankmanagement.model.Account;
import com.bank.bankmanagement.service.UserService;
import com.bank.bankmanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    // Отображение страницы с данными текущего пользователя и его аккаунтами
    @GetMapping
    public String showUserAndAccounts(Model model,
                                      @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        String username = userDetails.getUsername();
        // Предполагается, что имена пользователей уникальны
        User currentUser = userService.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        model.addAttribute("currentUser", currentUser);
        if (currentUser != null) {
            List<Account> accounts = accountService.getAllAccounts().stream()
                    .filter(a -> a.getUser().getId().equals(currentUser.getId()))
                    .collect(Collectors.toList());
            model.addAttribute("accounts", accounts);
        }
        return "users"; // шаблон: users.html
    }
}
