package com.meridian.api.account;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/users/{userId}/accounts")
    public ResponseEntity<AccountDTO> createAccountForUser(@PathVariable("userId") Long userId, @Valid @RequestBody AccountDTO accountDTO) {

        AccountDTO newAccount = accountService.createAccount(userId, accountDTO);

        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> deleteAccountById(@PathVariable("id") Long id) {

        accountService.deleteAccountById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/accounts")
    public ResponseEntity<List<AccountDTO>> getAllAccounts(@PathVariable("userId") Long userId) {

        return new ResponseEntity<>(accountService.getAccountsByUserId(userId), HttpStatus.OK);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable("id") Long id, @Valid @RequestBody AccountDTO accountDTO) {

        AccountDTO updatedAccount = accountService.updateAccount(accountDTO, id);

        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }
}
