package com.meridian.api.cards;

import java.util.List;

public interface CardService {

    CardResponseDTO createCard(Long userId, CardDTO cardDTO);

    void deleteCardById(Long id);

    List<CardResponseDTO> getCardsByUserId(Long userId);

    CardResponseDTO getCardById(Long id);
}
