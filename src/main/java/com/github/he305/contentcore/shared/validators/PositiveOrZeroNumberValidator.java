package com.github.he305.contentcore.shared.validators;

import com.github.he305.contentcore.shared.exceptions.ContentCoreArgumentException;

public class PositiveOrZeroNumberValidator {

    private PositiveOrZeroNumberValidator() {
    }

    public static int validate(int number) {
        if (number < 0) {
            throw new ContentCoreArgumentException("Number can't be lower than zero, " + number);
        }
        return number;
    }
}
