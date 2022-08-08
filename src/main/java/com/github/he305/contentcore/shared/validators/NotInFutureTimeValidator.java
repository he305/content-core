package com.github.he305.contentcore.shared.validators;

import com.github.he305.contentcore.shared.exceptions.ContentCoreArgumentException;

import java.time.LocalDateTime;

public class NotInFutureTimeValidator {
    private NotInFutureTimeValidator() {
    }


    public static LocalDateTime validate(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(time)) {
            throw new ContentCoreArgumentException("Time is in future");
        }
        return time;
    }
}
