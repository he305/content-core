package com.github.he305.contentcore.account.domain.model;

import com.github.he305.contentcore.shared.exceptions.StringInvalidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void register_invalidName() {
        String password = "pass";

        assertThrows(StringInvalidException.class, () ->
                Account.register(null, password));
    }

    @Test
    void register_invalidPassword() {
        String name = "name";

        assertThrows(StringInvalidException.class, () ->
                Account.register(name, null));
    }

    @Test
    void register_valid() {
        String name = "name";
        String password = "pass";
        assertDoesNotThrow(() -> Account.register(name, password));
    }
}