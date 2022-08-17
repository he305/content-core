package com.github.he305.contentcore.shared.validators;

import com.github.he305.contentcore.shared.exceptions.ContentCoreArgumentException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class NotInFutureTimeValidator {
    private NotInFutureTimeValidator() {
    }


    public static LocalDateTime validate(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        if (now.isBefore(time)) {
            throw new ContentCoreArgumentException("Time is in future");
        }
        return time;
    }
}
