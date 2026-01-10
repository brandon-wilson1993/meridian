package unit.com.meridian.api.account;

import com.meridian.api.account.AccountType;
import com.meridian.api.account.AccountTypeValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class AccountTypeValidatorTests {

    private static AccountTypeValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeAll
    static void setUp() {

        validator = new AccountTypeValidator();
    }

    @Test
    void testIsValid_WithNullValue_ReturnsTrue() {

        assertTrue(validator.isValid(null, context));
    }

    @Test
    void testIsValid_WithValidAccountType_ReturnsTrue() {

        for (AccountType type : AccountType.values()) {
            assertTrue(validator.isValid(type, context),
                    "Should return true for valid AccountType: " + type);
        }
    }
}
