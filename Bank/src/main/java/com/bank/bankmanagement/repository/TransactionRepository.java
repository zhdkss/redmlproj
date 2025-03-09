package com.bank.bankmanagement.repository;

import com.bank.bankmanagement.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromAccountId(Long fromAccountId);
    List<Transaction> findByToAccountId(Long toAccountId);
}
