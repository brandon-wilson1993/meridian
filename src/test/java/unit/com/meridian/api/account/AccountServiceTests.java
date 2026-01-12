package unit.com.meridian.api.account;

import com.meridian.api.account.*;
import com.meridian.api.errors.ResourceNotFoundException;
import com.meridian.api.users.UsersRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {

    private static Account account;
    private static AccountDTO accountDTO;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccountService accountService = new AccountServiceImpl();

    @BeforeAll
    static void beforeAll() {

        account = new Account();
        account.setId(1L);
        account.setUserId(1L);
        account.setAccountType(AccountType.SAVINGS);

        accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountType(AccountType.SAVINGS);
    }

    @Test
    void createAccount_shouldCreate_whenAccountDTOIsValid() {

        when(modelMapper.map(any(Account.class), eq(AccountDTO.class))).thenReturn(accountDTO);
        when(modelMapper.map(any(AccountDTO.class), eq(Account.class))).thenReturn(account);
        when(usersRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDTO result = accountService.createAccount(1L, modelMapper.map(account, AccountDTO.class));

        assertEquals(1L, result.getId());
        assertEquals(AccountType.SAVINGS, result.getAccountType());
    }

    @Test
    void createAccount_shouldThrowException_whenUserDoesNotExist() {

        when(usersRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> accountService.createAccount(1L, accountDTO));

        assertEquals("No user found with id 1", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void deleteAccountById_shouldDelete_whenIdExists() {

        when(accountRepository.existsById(1L)).thenReturn(true);

        accountService.deleteAccountById(1L);

        verify(accountRepository).deleteById(1L);
    }

    @Test
    void deleteAccountById_shouldNotDelete_whenIdDoesNotExist() {

        when(accountRepository.existsById(2L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> accountService.deleteAccountById(2L));

        assertEquals("Account with id 2 not found", exception.getMessage());
        verify(accountRepository, never()).deleteById(2L);
    }

    @Test
    void getAccountsByUserId_shouldReturnAccounts_whenUserExists() {

        Account account2 = new Account();
        account2.setId(2L);
        account2.setUserId(1L);
        account2.setAccountType(AccountType.CHECKING);

        AccountDTO accountDTO2 = new AccountDTO();
        accountDTO2.setId(2L);
        accountDTO2.setAccountType(AccountType.CHECKING);

        when(usersRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.findByUserId(1L)).thenReturn(List.of(account, account2));
        when(modelMapper.map(any(Account.class), eq(AccountDTO.class))).thenAnswer(invocation -> {
            Account acc = invocation.getArgument(0);
            return (acc != null && acc.getId() != null && acc.getId().equals(2L)) ? accountDTO2 : accountDTO;
        });

        List<AccountDTO> result = accountService.getAccountsByUserId(1L);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        assertEquals(AccountType.SAVINGS, result.get(0).getAccountType());
        assertEquals(AccountType.CHECKING, result.get(1).getAccountType());
    }

    @Test
    void getAccountsByUserId_shouldThrowException_whenUserDoesNotExist() {

        when(usersRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> accountService.getAccountsByUserId(1L));

        assertEquals("No user found with id 1", exception.getMessage());
        verify(accountRepository, never()).findByUserId(1L);
    }

    @Test
    void getAccountsByUserId_shouldReturnEmptyList_whenUserHasNoAccounts() {

        when(usersRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.findByUserId(1L)).thenReturn(Collections.emptyList());

        List<AccountDTO> result = accountService.getAccountsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(accountRepository).findByUserId(1L);
    }

    @Test
    void getAccountsByUserId_shouldReturnEmptyList_whenRepositoryReturnsNull() {

        when(usersRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.findByUserId(1L)).thenReturn(null);

        List<AccountDTO> result = accountService.getAccountsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(accountRepository).findByUserId(1L);
    }

    @Test
    void updateAccount_shouldUpdate_whenIdExists() {

        AccountDTO updatedAccountDTO = new AccountDTO();
        updatedAccountDTO.setAccountType(AccountType.TRADING);
        updatedAccountDTO.setId(1L);

        Account updatedAccount = new Account();
        updatedAccount.setId(1L);
        updatedAccount.setUserId(1L);
        updatedAccount.setAccountType(AccountType.TRADING);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(updatedAccount);
        when(modelMapper.map(any(Account.class), eq(AccountDTO.class))).thenReturn(updatedAccountDTO);

        AccountDTO result = accountService.updateAccount(updatedAccountDTO, 1L);

        assertEquals(1L, result.getId());
        assertEquals(AccountType.TRADING, result.getAccountType());
    }

    @Test
    void updateAccount_shouldThrowException_whenIdDoesNotExist() {

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> accountService.updateAccount(accountDTO, 1L));

        assertEquals("Account with id 1 not found", exception.getMessage());
        verify(accountRepository).findById(1L);
        verify(accountRepository, never()).save(any(Account.class));
    }
}
