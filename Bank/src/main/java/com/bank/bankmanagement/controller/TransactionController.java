package com.bank.bankmanagement.controller;

import com.bank.bankmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Отображение страницы со списком транзакций и формой для перевода
    @GetMapping
    public String showTransactions(Model model) {
        model.addAttribute("transactions", transactionService.getAllTransactions());
        return "transactions"; // шаблон: transactions.html
    }

    // Обработка формы перевода средств
    @PostMapping("/transfer")
    public String performTransfer(@RequestParam("fromAccountId") Long fromAccountId,
                                  @RequestParam("toAccountId") Long toAccountId,
                                  @RequestParam("amount") Double amount,
                                  Model model) {
        try {
            transactionService.performTransaction(fromAccountId, toAccountId, amount);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("transactions", transactionService.getAllTransactions());
            return "transactions";
        }
        return "redirect:/transactions";
    }
}
