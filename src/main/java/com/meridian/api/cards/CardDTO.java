package com.meridian.api.cards;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CardDTO {

    @NotNull(message = "accountId is required")
    private Long accountId;

    @NotNull(message = "cardNumber is required")
    @Pattern(regexp = "^\\d{16}$", message = "cardNumber must be 16 digits")
    private String cardNumber;

    @NotNull(message = "cardType is required")
    @ValidCardType
    private CardType cardType;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }
}
