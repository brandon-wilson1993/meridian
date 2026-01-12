package unit.com.meridian.api.account;

import com.meridian.api.account.Account;
import com.meridian.api.account.AccountType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountEntityTests {

    @Test
    void accountEntity_gettersAndSetters() {

        Account account = new Account();
        account.setId(1L);
        account.setUserId(1L);
        account.setAccountType(AccountType.SAVINGS);

        assertEquals(1L, account.getId());
        assertEquals(1L, account.getUserId());
        assertEquals(AccountType.SAVINGS, account.getAccountType());
    }
}
