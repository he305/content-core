package com.github.he305.contentcore.account.domain.model;

import com.github.he305.contentcore.account.domain.model.enums.Role;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    @Test
    void register() {
        String username = "user";
        String pass = "pass";
        Account actual = Account.register(username, pass);
        Account expected = new Account(actual.getId(), username, pass, Role.USER);
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Account.class).verify();
    }

    @Test
    void gettersTest() {
        String username = "user";
        String pass = "pass";
        Account actual = Account.register(username, pass);
        assertEquals(username, actual.getUsername());
        assertEquals(pass, actual.getPassword());
    }
}