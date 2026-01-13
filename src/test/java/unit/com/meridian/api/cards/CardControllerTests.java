package unit.com.meridian.api.cards;

import com.meridian.api.cards.*;
import com.meridian.api.errors.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardControllerTests {

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController = new CardController();

    @Test
    void cardController_createCard() {

        CardDTO cardDTO = new CardDTO();
        cardDTO.setAccountId(1L);
        cardDTO.setCardNumber("1234567890123456");
        cardDTO.setCardType(CardType.CREDIT);

        CardResponseDTO createdCard = new CardResponseDTO();
        createdCard.setId(1L);
        createdCard.setAccountId(1L);
        createdCard.setCardType(CardType.CREDIT);
        createdCard.setMaskedCardNumber("************3456");

        when(cardService.createCard(1L, cardDTO)).thenReturn(createdCard);

        ResponseEntity<CardResponseDTO> result = cardController.createCardForUser(1L, cardDTO);

        assertEquals(201, result.getStatusCode().value());
        assertEquals(1L, result.getBody().getId());
        assertEquals(CardType.CREDIT, result.getBody().getCardType());
        assertEquals("************3456", result.getBody().getMaskedCardNumber());
    }

    @Test
    void cardController_deleteCardById() {

        ResponseEntity<CardResponseDTO> result = cardController.deleteCardById(1L);

        assertEquals(200, result.getStatusCode().value());
        verify(cardService).deleteCardById(1L);
    }

    @Test
    void cardController_getAllCards() {

        CardResponseDTO card1 = new CardResponseDTO();
        card1.setId(1L);
        card1.setAccountId(1L);
        card1.setCardType(CardType.CREDIT);
        card1.setMaskedCardNumber("************1234");

        CardResponseDTO card2 = new CardResponseDTO();
        card2.setId(2L);
        card2.setAccountId(2L);
        card2.setCardType(CardType.DEBIT);
        card2.setMaskedCardNumber("************5678");

        List<CardResponseDTO> cards = List.of(card1, card2);

        when(cardService.getCardsByUserId(1L)).thenReturn(cards);

        ResponseEntity<List<CardResponseDTO>> result = cardController.getAllCards(1L);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(2, result.getBody().size());
        assertEquals(1L, result.getBody().get(0).getId());
        assertEquals(CardType.CREDIT, result.getBody().get(0).getCardType());
        assertEquals(2L, result.getBody().get(1).getId());
        assertEquals(CardType.DEBIT, result.getBody().get(1).getCardType());
    }

    @Test
    void cardController_getCardById() {

        CardResponseDTO card = new CardResponseDTO();
        card.setId(1L);
        card.setAccountId(1L);
        card.setCardType(CardType.CREDIT);
        card.setMaskedCardNumber("************1234");

        when(cardService.getCardById(1L)).thenReturn(card);

        ResponseEntity<CardResponseDTO> result = cardController.getCardById(1L);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1L, result.getBody().getId());
        assertEquals(CardType.CREDIT, result.getBody().getCardType());
    }

    @Test
    void cardController_getAllCards_shouldReturnEmptyList() {

        when(cardService.getCardsByUserId(1L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<CardResponseDTO>> result = cardController.getAllCards(1L);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
    }

    @Test
    void cardController_createCard_shouldThrowException_whenUserNotFound() {

        CardDTO cardDTO = new CardDTO();
        cardDTO.setAccountId(1L);
        cardDTO.setCardNumber("1234567890123456");
        cardDTO.setCardType(CardType.CREDIT);

        when(cardService.createCard(1L, cardDTO))
                .thenThrow(new ResourceNotFoundException("No user found with id 1"));

        assertThrows(ResourceNotFoundException.class, 
                () -> cardController.createCardForUser(1L, cardDTO));
    }

    @Test
    void cardController_deleteCardById_shouldThrowException_whenCardNotFound() {

        doThrow(new ResourceNotFoundException("Card with id 1 not found"))
                .when(cardService).deleteCardById(1L);

        assertThrows(ResourceNotFoundException.class, 
                () -> cardController.deleteCardById(1L));
    }

    @Test
    void cardController_getCardById_shouldThrowException_whenCardNotFound() {

        when(cardService.getCardById(1L))
                .thenThrow(new ResourceNotFoundException("Card with id 1 not found"));

        assertThrows(ResourceNotFoundException.class, 
                () -> cardController.getCardById(1L));
    }
}
