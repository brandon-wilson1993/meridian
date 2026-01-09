package unit.com.meridian.api.account;

import com.meridian.api.account.*;
import com.meridian.api.users.UsersRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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

        assertThrows(RuntimeException.class, () -> accountService.createAccount(1L, accountDTO));

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

        assertThrows(RuntimeException.class, () -> accountService.deleteAccountById(2L));

        verify(accountRepository, never()).deleteById(2L);
    }
}
