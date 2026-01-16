package com.meridian.api.cards;

public interface CardsService {

    CardsDTO createCard(Long userId, Long accountId, CardsDTO card);

    void deleteCardById(Long id);

    CardsDTO getCardById(Long id);

    // add get cards by user id
    // add get cards by account id

    CardsDTO updateCard(CardsDTO updatedCard, Long id);
}
