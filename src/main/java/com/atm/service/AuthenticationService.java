package com.atm.service;

import com.atm.domain.Card;
import com.atm.domain.User;
import com.atm.repository.CardRepository;
import com.atm.repository.UserRepository;

public class AuthenticationService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public AuthenticationService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public User authenticate(String cardNumber, String pin) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (card == null) {
            return null; // Card not found
        }

        User user = userRepository.findById(card.getUserId());
        if (user != null && user.verifyPin(pin)) {
            return user;
        }

        return null; // Authentication failed
    }
}