package unit.com.meridian.api.cards;

import com.meridian.api.cards.Card;
import com.meridian.api.cards.CardType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CardEntityTests {

    @Test
    void cardEntity_gettersAndSetters() {

        Card card = new Card();
        card.setId(1L);
        card.setAccountId(100L);
        card.setUserId(200L);
        card.setCardNumberHash("hashed_card_number");
        card.setCardType(CardType.CREDIT);
        
        LocalDateTime now = LocalDateTime.now();
        card.setCreatedAt(now);
        card.setUpdatedAt(now);

        assertEquals(1L, card.getId());
        assertEquals(100L, card.getAccountId());
        assertEquals(200L, card.getUserId());
        assertEquals("hashed_card_number", card.getCardNumberHash());
        assertEquals(CardType.CREDIT, card.getCardType());
        assertEquals(now, card.getCreatedAt());
        assertEquals(now, card.getUpdatedAt());
    }

    @Test
    void cardEntity_withDebitCardType() {

        Card card = new Card();
        card.setId(2L);
        card.setAccountId(101L);
        card.setUserId(201L);
        card.setCardNumberHash("another_hashed_card");
        card.setCardType(CardType.DEBIT);

        assertEquals(2L, card.getId());
        assertEquals(101L, card.getAccountId());
        assertEquals(201L, card.getUserId());
        assertEquals("another_hashed_card", card.getCardNumberHash());
        assertEquals(CardType.DEBIT, card.getCardType());
    }

    @Test
    void cardEntity_timestampsAreSet() {

        Card card = new Card();
        LocalDateTime now = LocalDateTime.now();
        card.setCreatedAt(now);
        card.setUpdatedAt(now);

        assertNotNull(card.getCreatedAt());
        assertNotNull(card.getUpdatedAt());
        assertEquals(now, card.getCreatedAt());
        assertEquals(now, card.getUpdatedAt());
    }
}
