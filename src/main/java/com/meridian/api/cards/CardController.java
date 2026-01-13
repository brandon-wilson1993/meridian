package com.meridian.api.cards;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/users/{userId}/cards")
    public ResponseEntity<CardResponseDTO> createCardForUser(@PathVariable("userId") Long userId, @Valid @RequestBody CardDTO cardDTO) {

        CardResponseDTO newCard = cardService.createCard(userId, cardDTO);

        return new ResponseEntity<>(newCard, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/cards")
    public ResponseEntity<List<CardResponseDTO>> getAllCards(@PathVariable("userId") Long userId) {

        return new ResponseEntity<>(cardService.getCardsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<CardResponseDTO> getCardById(@PathVariable("id") Long id) {

        CardResponseDTO card = cardService.getCardById(id);

        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<CardResponseDTO> deleteCardById(@PathVariable("id") Long id) {

        cardService.deleteCardById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
