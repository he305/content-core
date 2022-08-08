package com.github.he305.contentcore.shared.validators;

import com.github.he305.contentcore.shared.exceptions.ContentCoreArgumentException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NotInFutureTimeValidatorTest {

    @Test
    void validate_notValid() {
        LocalDateTime time = LocalDateTime.now().plus(1, ChronoUnit.MINUTES);
        assertThrows(ContentCoreArgumentException.class, () -> NotInFutureTimeValidator.validate(time));
    }

    @Test
    void validate_valid() {
        LocalDateTime time = LocalDateTime.now().minus(1, ChronoUnit.SECONDS);
        LocalDateTime actual = NotInFutureTimeValidator.validate(time);
        assertEquals(time, actual);
    }
}