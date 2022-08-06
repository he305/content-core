package com.github.he305.contentcore.shared.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PositiveOrZeroNumberValidatorTest {

    @Test
    void validate_valid() {
        int number = 9999999;
        int actual = PositiveOrZeroNumberValidator.validate(number);
        assertEquals(number, actual);
    }

    @Test
    void validate_zeroValid() {
        int number = 0;
        int actual = PositiveOrZeroNumberValidator.validate(number);
        assertEquals(number, actual);
    }

    @Test
    void validate_notValid() {
        int number = -1;
        assertThrows(IllegalArgumentException.class, () -> PositiveOrZeroNumberValidator.validate(number));
    }
}