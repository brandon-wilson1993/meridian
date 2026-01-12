package unit.com.meridian.api.account;

import com.meridian.api.account.AccountController;
import com.meridian.api.account.AccountDTO;
import com.meridian.api.account.AccountService;
import com.meridian.api.account.AccountType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTests {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController = new AccountController();

    @Test
    void accountController_createAccount() {

        AccountDTO account = new AccountDTO();
        account.setId(null);
        account.setAccountType(AccountType.SAVINGS);

        AccountDTO createdAccount = new AccountDTO();
        createdAccount.setId(1L);
        createdAccount.setAccountType(AccountType.SAVINGS);

        when(accountService.createAccount(1L, account)).thenReturn(createdAccount);

        ResponseEntity<AccountDTO> result = accountController.createAccountForUser(1L, account);

        assertEquals(201, result.getStatusCode().value());
        assertEquals(1L, result.getBody().getId());
        assertEquals(AccountType.SAVINGS, result.getBody().getAccountType());
    }

    @Test
    void accountController_deleteAccountById() {

        ResponseEntity<AccountDTO> result = accountController.deleteAccountById(1L);

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void accountController_getAllAccounts() {

        AccountDTO account1 = new AccountDTO();
        account1.setId(1L);
        account1.setAccountType(AccountType.SAVINGS);

        AccountDTO account2 = new AccountDTO();
        account2.setId(2L);
        account2.setAccountType(AccountType.CHECKING);

        List<AccountDTO> accounts = List.of(account1, account2);

        when(accountService.getAccountsByUserId(1L)).thenReturn(accounts);

        ResponseEntity<List<AccountDTO>> result = accountController.getAllAccounts(1L);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(2, result.getBody().size());
        assertEquals(1L, result.getBody().get(0).getId());
        assertEquals(AccountType.SAVINGS, result.getBody().get(0).getAccountType());
        assertEquals(2L, result.getBody().get(1).getId());
        assertEquals(AccountType.CHECKING, result.getBody().get(1).getAccountType());
    }

    @Test
    void accountController_updateAccount() {

        AccountDTO updatedAccount = new AccountDTO();
        updatedAccount.setId(1L);
        updatedAccount.setAccountType(AccountType.CHECKING);

        when(accountService.updateAccount(updatedAccount, 1L)).thenReturn(updatedAccount);

        ResponseEntity<AccountDTO> result = accountController.updateAccount(1L, updatedAccount);

        assertEquals(201, result.getStatusCode().value());
        assertEquals(1L, result.getBody().getId());
        assertEquals(AccountType.CHECKING, result.getBody().getAccountType());
    }
}
