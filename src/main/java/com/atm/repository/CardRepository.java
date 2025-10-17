package com.atm.repository;

import com.atm.domain.Card;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CardRepository {
    private final Map<String, Card> cards = new ConcurrentHashMap<>();

    public Card findByCardNumber(String cardNumber) {
        return cards.get(cardNumber);
    }

    public void save(Card card) {
        cards.put(card.getCardNumber(), card);
    }
}