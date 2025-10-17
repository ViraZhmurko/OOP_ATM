package com.atm.service;

import com.atm.domain.Card;
import com.atm.domain.User;
import com.atm.repository.CardRepository;
import com.atm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {

    private AuthenticationService authService;
    private CardRepository cardRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        cardRepository = new CardRepository();
        userRepository = new UserRepository();
        authService = new AuthenticationService(cardRepository, userRepository);

        User user = new User("user1", "Jane Doe", "5678");
        userRepository.save(user);

        Card card = new Card("5555-6666-7777-8888", "acc2", "user1");
        cardRepository.save(card);
    }

    @Test
    void testSuccessfulAuthentication() {
        User authenticatedUser = authService.authenticate("5555-6666-7777-8888", "5678");
        assertNotNull(authenticatedUser);
        assertEquals("Jane Doe", authenticatedUser.getName());
    }

    @Test
    void testAuthenticationWithInvalidPin() {
        User authenticatedUser = authService.authenticate("5555-6666-7777-8888", "0000");
        assertNull(authenticatedUser);
    }

    @Test
    void testAuthenticationWithInvalidCard() {
        User authenticatedUser = authService.authenticate("0000-0000-0000-0000", "5678");
        assertNull(authenticatedUser);
    }
}