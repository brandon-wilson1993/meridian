package unit.com.meridian.api.cards;

import com.meridian.api.account.Account;
import com.meridian.api.account.AccountRepository;
import com.meridian.api.account.AccountType;
import com.meridian.api.cards.*;
import com.meridian.api.errors.ResourceNotFoundException;
import com.meridian.api.users.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceTests {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CardService cardService = new CardServiceImpl();

    @Test
    void createCard_shouldCreate_whenCardDTOIsValid() {

        CardDTO cardDTO = new CardDTO();
        cardDTO.setAccountId(1L);
        cardDTO.setCardNumber("1234567890123456");
        cardDTO.setCardType(CardType.CREDIT);

        Account account = new Account();
        account.setId(1L);
        account.setUserId(1L);
        account.setAccountType(AccountType.CREDIT);

        Card savedCard = new Card();
        savedCard.setId(1L);
        savedCard.setUserId(1L);
        savedCard.setAccountId(1L);
        savedCard.setCardType(CardType.CREDIT);
        savedCard.setCardNumberHash("somehash");

        when(usersRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(cardRepository.save(any(Card.class))).thenReturn(savedCard);

        CardResponseDTO result = cardService.createCard(1L, cardDTO);

        assertEquals(1L, result.getId());
        assertEquals(CardType.CREDIT, result.getCardType());
        assertEquals("************3456", result.getMaskedCardNumber());
        verify(cardRepository).save(any(Card.class));
    }

    @Test
    void createCard_shouldThrowException_whenUserDoesNotExist() {

        CardDTO cardDTO = new CardDTO();
        cardDTO.setAccountId(1L);
        cardDTO.setCardNumber("1234567890123456");
        cardDTO.setCardType(CardType.CREDIT);

        when(usersRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> cardService.createCard(1L, cardDTO));

        assertEquals("No user found with id 1", exception.getMessage());
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void createCard_shouldThrowException_whenAccountDoesNotExist() {

        CardDTO cardDTO = new CardDTO();
        cardDTO.setAccountId(1L);
        cardDTO.setCardNumber("1234567890123456");
        cardDTO.setCardType(CardType.CREDIT);

        when(usersRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> cardService.createCard(1L, cardDTO));

        assertEquals("Account with id 1 not found", exception.getMessage());
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void createCard_shouldThrowException_whenAccountDoesNotBelongToUser() {

        CardDTO cardDTO = new CardDTO();
        cardDTO.setAccountId(1L);
        cardDTO.setCardNumber("1234567890123456");
        cardDTO.setCardType(CardType.CREDIT);

        Account account = new Account();
        account.setId(1L);
        account.setUserId(2L); // Different user
        account.setAccountType(AccountType.CREDIT);

        when(usersRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> cardService.createCard(1L, cardDTO));

        assertEquals("Account with id 1 does not belong to user 1", exception.getMessage());
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void createCard_shouldThrowException_whenCardTypeDoesNotMatchAccountType_creditCardSavingsAccount() {

        CardDTO cardDTO = new CardDTO();
        cardDTO.setAccountId(1L);
        cardDTO.setCardNumber("1234567890123456");
        cardDTO.setCardType(CardType.CREDIT);

        Account account = new Account();
        account.setId(1L);
        account.setUserId(1L);
        account.setAccountType(AccountType.SAVINGS); // Wrong account type

        when(usersRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> cardService.createCard(1L, cardDTO));

        assertEquals("Card type CREDIT does not match account type SAVINGS", exception.getMessage());
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void createCard_shouldThrowException_whenCardTypeDoesNotMatchAccountType_debitCardCheckingAccount() {

        CardDTO cardDTO = new CardDTO();
        cardDTO.setAccountId(1L);
        cardDTO.setCardNumber("1234567890123456");
        cardDTO.setCardType(CardType.DEBIT);

        Account account = new Account();
        account.setId(1L);
        account.setUserId(1L);
        account.setAccountType(AccountType.CHECKING); // Wrong account type

        when(usersRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> cardService.createCard(1L, cardDTO));

        assertEquals("Card type DEBIT does not match account type CHECKING", exception.getMessage());
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void createCard_shouldCreate_whenDebitCardAndDebitAccount() {

        CardDTO cardDTO = new CardDTO();
        cardDTO.setAccountId(1L);
        cardDTO.setCardNumber("1234567890123456");
        cardDTO.setCardType(CardType.DEBIT);

        Account account = new Account();
        account.setId(1L);
        account.setUserId(1L);
        account.setAccountType(AccountType.DEBIT);

        Card savedCard = new Card();
        savedCard.setId(1L);
        savedCard.setUserId(1L);
        savedCard.setAccountId(1L);
        savedCard.setCardType(CardType.DEBIT);
        savedCard.setCardNumberHash("somehash");

        when(usersRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(cardRepository.save(any(Card.class))).thenReturn(savedCard);

        CardResponseDTO result = cardService.createCard(1L, cardDTO);

        assertEquals(1L, result.getId());
        assertEquals(CardType.DEBIT, result.getCardType());
        verify(cardRepository).save(any(Card.class));
    }

    @Test
    void deleteCardById_shouldDelete_whenIdExists() {

        when(cardRepository.existsById(1L)).thenReturn(true);

        cardService.deleteCardById(1L);

        verify(cardRepository).deleteById(1L);
    }

    @Test
    void deleteCardById_shouldNotDelete_whenIdDoesNotExist() {

        when(cardRepository.existsById(2L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> cardService.deleteCardById(2L));

        assertEquals("Card with id 2 not found", exception.getMessage());
        verify(cardRepository, never()).deleteById(2L);
    }

    @Test
    void getCardsByUserId_shouldReturnCards_whenUserExists() {

        Card card1 = new Card();
        card1.setId(1L);
        card1.setUserId(1L);
        card1.setAccountId(1L);
        card1.setCardType(CardType.CREDIT);
        card1.setCardNumberHash("hash1");

        Card card2 = new Card();
        card2.setId(2L);
        card2.setUserId(1L);
        card2.setAccountId(2L);
        card2.setCardType(CardType.DEBIT);
        card2.setCardNumberHash("hash2");

        when(usersRepository.existsById(1L)).thenReturn(true);
        when(cardRepository.findByUserId(1L)).thenReturn(List.of(card1, card2));

        List<CardResponseDTO> result = cardService.getCardsByUserId(1L);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        assertEquals(CardType.CREDIT, result.get(0).getCardType());
        assertEquals(CardType.DEBIT, result.get(1).getCardType());
    }

    @Test
    void getCardsByUserId_shouldThrowException_whenUserDoesNotExist() {

        when(usersRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> cardService.getCardsByUserId(1L));

        assertEquals("No user found with id 1", exception.getMessage());
        verify(cardRepository, never()).findByUserId(1L);
    }

    @Test
    void getCardsByUserId_shouldReturnEmptyList_whenUserHasNoCards() {

        when(usersRepository.existsById(1L)).thenReturn(true);
        when(cardRepository.findByUserId(1L)).thenReturn(Collections.emptyList());

        List<CardResponseDTO> result = cardService.getCardsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cardRepository).findByUserId(1L);
    }

    @Test
    void getCardsByUserId_shouldReturnEmptyList_whenRepositoryReturnsNull() {

        when(usersRepository.existsById(1L)).thenReturn(true);
        when(cardRepository.findByUserId(1L)).thenReturn(null);

        List<CardResponseDTO> result = cardService.getCardsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cardRepository).findByUserId(1L);
    }

    @Test
    void getCardById_shouldReturnCard_whenIdExists() {

        Card card = new Card();
        card.setId(1L);
        card.setUserId(1L);
        card.setAccountId(1L);
        card.setCardType(CardType.CREDIT);
        card.setCardNumberHash("hash");

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        CardResponseDTO result = cardService.getCardById(1L);

        assertEquals(1L, result.getId());
        assertEquals(CardType.CREDIT, result.getCardType());
        verify(cardRepository).findById(1L);
    }

    @Test
    void getCardById_shouldThrowException_whenIdDoesNotExist() {

        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> cardService.getCardById(1L));

        assertEquals("Card with id 1 not found", exception.getMessage());
        verify(cardRepository).findById(1L);
    }
}
