package unit.com.meridian.api.cards;

import com.meridian.api.cards.CardType;
import com.meridian.api.cards.CardTypeValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CardTypeValidatorTests {

    private static CardTypeValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeAll
    static void setUp() {

        validator = new CardTypeValidator();
    }

    @Test
    void testIsValid_WithNullValue_ReturnsTrue() {

        assertTrue(validator.isValid(null, context));
    }

    @Test
    void testIsValid_WithValidCardType_ReturnsTrue() {

        for (CardType type : CardType.values()) {
            assertTrue(validator.isValid(type, context),
                    "Should return true for valid CardType: " + type);
        }
    }
}
