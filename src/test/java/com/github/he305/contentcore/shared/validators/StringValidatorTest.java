package com.github.he305.contentcore.shared.validators;

import com.github.he305.contentcore.shared.exceptions.ContentCoreArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringValidatorTest {

    @Test
    void isNullOrEmpty_nullString() {
        assertThrows(ContentCoreArgumentException.class, () ->
                StringValidator.isNullOrEmpty(null));
    }

    @Test
    void isNullOrEmpty_blank() {
        String blank = "";
        assertThrows(ContentCoreArgumentException.class, () ->
                StringValidator.isNullOrEmpty(blank));
    }

    @Test
    void isNullOrEmpty_whitespace() {
        String whitespace = " ";
        assertThrows(ContentCoreArgumentException.class, () ->
                StringValidator.isNullOrEmpty(whitespace));
    }

    @Test
    void isNullOrEmpty_valid() {
        String valid = "valid";
        String actual = StringValidator.isNullOrEmpty(valid);

        assertEquals(valid, actual);
    }
}