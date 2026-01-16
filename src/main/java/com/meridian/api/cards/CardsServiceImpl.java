package com.meridian.api.cards;

import com.meridian.api.account.AccountRepository;
import com.meridian.api.errors.ResourceNotFoundException;
import com.meridian.api.users.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardsServiceImpl implements CardsService {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ModelMapper modelMapper;

    // TODO: user id and account id should be within the request body
    public CardsDTO createCard(Long userId, Long accountId, CardsDTO cardDTO) {

        if(!usersRepository.existsById(userId)) {

             throw new ResourceNotFoundException("No user found with id " + userId);
        }

        if(!accountRepository.existsById(accountId)) {

             throw new ResourceNotFoundException("No account found with id " + accountId);
        }

         Cards cards = modelMapper.map(cardDTO, Cards.class);
         cards.setUserId(userId);

         Cards savedCard = cardsRepository.save(cards);

         return modelMapper.map(savedCard, CardsDTO.class);
    }

    public void deleteCardById(Long id) {

    }

    public CardsDTO getCardById(Long id) {
        return null;
    }

    public CardsDTO updateCard(CardsDTO updatedCard, Long id) {
        return null;
    }
}
