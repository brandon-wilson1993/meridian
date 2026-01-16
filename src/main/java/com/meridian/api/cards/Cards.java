package com.meridian.api.cards;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "cards")
public class Cards {

    @Id
    @SequenceGenerator(name = "idx_seq", sequenceName = "idx_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idx_seq")
    private Long id;

    // use this to avoid going to Stripe/Braintree for everything
    @Column(name = "card_token", nullable = false, unique = true)
    private UUID cardToken;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "card_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(name = "last_four_digits", nullable = false, length = 4)
    private String lastFourDigits;

    @Column(name = "expiry_month", nullable = false)
    private Integer expiryMonth;

    @Column(name = "expiry_year", nullable = false)
    private Integer expiryYear;

    // only use this for transaction processing (need to verify the card is correct when
    // processing transactions)
    @Column(name = "processor_token", nullable = false)
    private String processorToken; // Token from Stripe/Braintree

    @Column(name = "card_brand")
    private CardBrand cardBrand; // VISA, MASTERCARD, etc.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getCardToken() {
        return cardToken;
    }

    public void setCardToken(UUID cardToken) {
        this.cardToken = cardToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getProcessorToken() {
        return processorToken;
    }

    public void setProcessorToken(String processorToken) {
        this.processorToken = processorToken;
    }

    public CardBrand getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(CardBrand cardBrand) {
        this.cardBrand = cardBrand;
    }
}

