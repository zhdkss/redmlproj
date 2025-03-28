package com.bank.bankmanagement.controller;

import com.bank.bankmanagement.model.Account;
import com.bank.bankmanagement.model.User;
import com.bank.bankmanagement.service.AccountService;
import com.bank.bankmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    // Отображение страницы со списком аккаунтов и формы для создания нового аккаунта
    @GetMapping
    public String showAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        model.addAttribute("account", new Account());
        return "accounts"; // шаблон: accounts.html
    }

    // Обработка формы создания нового аккаунта
    @PostMapping
    public String createAccount(@ModelAttribute("account") Account account,
                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                                Model model) {
        String username = userDetails.getUsername();
        User currentUser = userService.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if (currentUser == null) {
            throw new RuntimeException("Текущий пользователь не найден");
        }
        account.setUser(currentUser);
        try {
            accountService.createAccount(account);
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("accounts", accountService.getAllAccounts());
            return "accounts";
        }
        return "redirect:/accounts";
    }

    // Новый метод для удаления аккаунта
    @PostMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return "redirect:/accounts";
    }
}
