package com.github.he305.contentcore.shared.validators;

import com.github.he305.contentcore.shared.exceptions.StringInvalidException;

public class StringValidator {
    private StringValidator() {
    }

    public static String isNullOrEmpty(String instance) {
        if (instance == null || instance.isEmpty()) {
            throw new StringInvalidException();
        }
        return instance;
    }
}
