package com.meridian.api.account;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Account {

    @SequenceGenerator(name = "idx_seq", sequenceName = "idx_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idx_seq")
    @Id
    private Long id;

    @Column(name = "account_type", nullable = false)
    private AccountType accountType;
}
