package com.meridian.api.account;

import com.meridian.api.users.UsersRepository;
import com.meridian.api.users.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AccountDTO createAccount(Long userId, AccountDTO accountDTO) {

        if (!usersRepository.existsById(userId)) {

            throw new RuntimeException("No user found with id " + userId);
        }

        Account account = modelMapper.map(accountDTO, Account.class);
        account.setUserId(userId);

        Account savedAccount = accountRepository.save(account);

        return modelMapper.map(savedAccount, AccountDTO.class);
    }

    public void deleteAccountById(Long id) {

        if (!accountRepository.existsById(id)) {

            throw new RuntimeException("Account with id " + id + " not found");
        }

        accountRepository.deleteById(id);
    }

    public List<AccountDTO> getAccountsByUserId(Long userId) {

        if (!usersRepository.existsById(userId)) {

            throw new RuntimeException("No user found with id " + userId);
        }

        List<Account> accounts = accountRepository.findByUserId(userId);
        if (accounts == null || accounts.isEmpty()) {
            return List.of();
        }

        return accounts.stream()
                .map(account -> modelMapper.map(account, AccountDTO.class))
                .collect(java.util.stream.Collectors.toList());
    }

    public AccountDTO updateAccount(AccountDTO updatedAccount, Long id) {

        Optional<Account> account = accountRepository.findById(id);

        return account
                .map(
                        acc -> {
                            acc.setAccountType(updatedAccount.getAccountType());
                            Account savedAccount = accountRepository.save(acc);
                            return modelMapper.map(savedAccount, AccountDTO.class);
                        })
                .orElseThrow(
                        () -> new RuntimeException("Account with id " + id + " not found"));
    }
}
