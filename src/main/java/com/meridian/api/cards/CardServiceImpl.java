package com.meridian.api.cards;

import com.meridian.api.account.Account;
import com.meridian.api.account.AccountRepository;
import com.meridian.api.account.AccountType;
import com.meridian.api.errors.ResourceNotFoundException;
import com.meridian.api.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AccountRepository accountRepository;

    public CardResponseDTO createCard(Long userId, CardDTO cardDTO) {

        // Validate that user exists
        if (!usersRepository.existsById(userId)) {
            throw new ResourceNotFoundException("No user found with id " + userId);
        }

        // Validate that account exists
        Optional<Account> accountOptional = accountRepository.findById(cardDTO.getAccountId());
        if (accountOptional.isEmpty()) {
            throw new ResourceNotFoundException("Account with id " + cardDTO.getAccountId() + " not found");
        }

        Account account = accountOptional.get();

        // Validate that account belongs to the user
        if (!account.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Account with id " + cardDTO.getAccountId() + " does not belong to user " + userId);
        }

        // Validate that card type matches account type
        AccountType accountType = account.getAccountType();
        CardType cardType = cardDTO.getCardType();

        if ((cardType == CardType.CREDIT && accountType != AccountType.CREDIT) ||
            (cardType == CardType.DEBIT && accountType != AccountType.DEBIT)) {
            throw new IllegalArgumentException("Card type " + cardType + " does not match account type " + accountType);
        }

        // Hash the card number using SHA-256
        String cardNumberHash = hashCardNumber(cardDTO.getCardNumber());

        // Create and save the card
        Card card = new Card();
        card.setUserId(userId);
        card.setAccountId(cardDTO.getAccountId());
        card.setCardType(cardDTO.getCardType());
        card.setCardNumberHash(cardNumberHash);

        Card savedCard = cardRepository.save(card);

        return mapToResponseDTO(savedCard, cardDTO.getCardNumber());
    }

    public void deleteCardById(Long id) {

        if (!cardRepository.existsById(id)) {
            throw new ResourceNotFoundException("Card with id " + id + " not found");
        }

        cardRepository.deleteById(id);
    }

    public List<CardResponseDTO> getCardsByUserId(Long userId) {

        if (!usersRepository.existsById(userId)) {
            throw new ResourceNotFoundException("No user found with id " + userId);
        }

        List<Card> cards = cardRepository.findByUserId(userId);
        if (cards == null || cards.isEmpty()) {
            return List.of();
        }

        return cards.stream()
                .map(card -> mapToResponseDTO(card, null))
                .collect(Collectors.toList());
    }

    public CardResponseDTO getCardById(Long id) {

        Optional<Card> cardOptional = cardRepository.findById(id);
        
        if (cardOptional.isEmpty()) {
            throw new ResourceNotFoundException("Card with id " + id + " not found");
        }

        return mapToResponseDTO(cardOptional.get(), null);
    }

    private String hashCardNumber(String cardNumber) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(cardNumber.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    private CardResponseDTO mapToResponseDTO(Card card, String originalCardNumber) {
        CardResponseDTO dto = new CardResponseDTO();
        dto.setId(card.getId());
        dto.setAccountId(card.getAccountId());
        dto.setCardType(card.getCardType());
        
        // If we have the original card number, mask it. Otherwise, show generic mask
        if (originalCardNumber != null && originalCardNumber.length() >= 4) {
            String lastFour = originalCardNumber.substring(originalCardNumber.length() - 4);
            dto.setMaskedCardNumber("************" + lastFour);
        } else {
            dto.setMaskedCardNumber("************XXXX");
        }
        
        return dto;
    }
}
