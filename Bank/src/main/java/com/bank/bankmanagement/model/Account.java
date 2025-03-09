package com.bank.bankmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List; // <--- Не забудь импортировать java.util.List

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private String accountType; // "CHECKING" или "SAVINGS"

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL)
    private List<Transaction> sentTransactions;

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
    private List<Transaction> receivedTransactions;
}
