package com.github.he305.contentcore.shared.validators;

public class StringValidator {
    private StringValidator() {
    }

    public static String isNullOrEmpty(String instance) {
        if (instance == null || instance.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return instance;
    }
}
