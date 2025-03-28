package com.bank.bankmanagement.service;

import com.bank.bankmanagement.model.Account;
import com.bank.bankmanagement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        Optional<Account> existing = accountRepository.findByAccountNumber(account.getAccountNumber());
        if (existing.isPresent()) {
            throw new DataIntegrityViolationException("Счет с таким номером уже существует");
        }
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
