package com.meridian.api.account;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountDTO {

    private Long id;

    @NotNull(message = "accountType is required")
    @ValidAccountType
    private AccountType accountType;

    public Long getId() {
        return id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
