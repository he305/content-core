package com.github.he305.contentcore.shared.validators;

import java.time.LocalDateTime;

public class NotInFutureTimeValidator {
    private NotInFutureTimeValidator() {
    }

    
    public static LocalDateTime validate(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(time)) {
            throw new IllegalArgumentException("Time is in future");
        }
        return time;
    }
}
