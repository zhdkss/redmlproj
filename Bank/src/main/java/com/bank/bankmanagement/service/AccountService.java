package com.bank.bankmanagement.service;

import com.bank.bankmanagement.model.Account;
import com.bank.bankmanagement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
}
