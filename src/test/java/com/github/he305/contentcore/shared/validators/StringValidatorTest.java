package com.github.he305.contentcore.shared.validators;

import com.github.he305.contentcore.shared.exceptions.StringInvalidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringValidatorTest {

    @Test
    void isNullOrEmpty_valid() {
        String validString = "valid";

        String actual = StringValidator.isNullOrEmpty(validString);
        assertEquals(validString, actual);
    }

    @Test
    void isNullOrEmpty_nullString_shouldThrow() {
        assertThrows(StringInvalidException.class, () ->
                StringValidator.isNullOrEmpty(null));
    }

    @Test
    void isNullOrEmpty_blankString_shouldThrow() {
        String blank = "";
        assertThrows(StringInvalidException.class, () ->
                StringValidator.isNullOrEmpty(blank));
    }

}