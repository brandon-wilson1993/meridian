package com.meridian.api.account;

import java.util.List;

public interface AccountService {

    AccountDTO createAccount(Long userId, AccountDTO account);

    void deleteAccountById(Long id);

    List<AccountDTO> getAccountsByUserId(Long userId);

    AccountDTO updateAccount(AccountDTO updatedAccount, Long id);
}
