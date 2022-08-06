package com.github.he305.contentcore.shared.validators;

public class PositiveOrZeroNumberValidator {

    private PositiveOrZeroNumberValidator() {
    }

    public static int validate(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number can't be lower than zero, " + number);
        }
        return number;
    }
}
