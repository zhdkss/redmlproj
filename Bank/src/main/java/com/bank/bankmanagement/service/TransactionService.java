package com.bank.bankmanagement.service;

import com.bank.bankmanagement.model.Account;
import com.bank.bankmanagement.model.Transaction;
import com.bank.bankmanagement.repository.AccountRepository;
import com.bank.bankmanagement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    /**
     * Выполнение перевода средств с одного счета на другой.
     * Если средств недостаточно, либо один из счетов не найден, выбрасывается исключение.
     */
    @Transactional
    public Transaction performTransaction(Long fromAccountId, Long toAccountId, Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        }

        // Получаем счета отправителя и получателя
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Счет отправителя не найден"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Счет получателя не найден"));

        // Проверка наличия средств на счете отправителя
        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Недостаточно средств на счете отправителя");
        }

        // Обновляем балансы счетов
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Создаем объект транзакции
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        // Сохраняем транзакцию в БД
        return transactionRepository.save(transaction);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
