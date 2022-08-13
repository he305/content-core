package com.github.he305.contentcore.shared.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringValidatorTest {

    @Test
    void isNullOrEmpty_nullString() {
        assertThrows(IllegalArgumentException.class, () ->
                StringValidator.isNullOrEmpty(null));
    }

    @Test
    void isNullOrEmpty_blank() {
        String blank = "";
        assertThrows(IllegalArgumentException.class, () ->
                StringValidator.isNullOrEmpty(blank));
    }

    @Test
    void isNullOrEmpty_whitespace() {
        String whitespace = " ";
        assertThrows(IllegalArgumentException.class, () ->
                StringValidator.isNullOrEmpty(whitespace));
    }

    @Test
    void isNullOrEmpty_valid() {
        String valid = "valid";
        String actual = StringValidator.isNullOrEmpty(valid);

        assertEquals(valid, actual);
    }

    @Test
    void isNullOrEmpty_japanese_valid() {
        String valid = "ヴェノム２ 同時視聴\uD83C\uDFA5";
        String actual = StringValidator.isNullOrEmpty(valid);
        assertEquals(valid, actual);
    }
}