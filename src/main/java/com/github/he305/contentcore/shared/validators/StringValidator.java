package com.github.he305.contentcore.shared.validators;

import com.github.he305.contentcore.shared.exceptions.ContentCoreArgumentException;

public class StringValidator {
    private StringValidator() {
    }

    public static String isNullOrEmpty(String instance) {
        if (instance == null || instance.trim().isEmpty()) {
            throw new ContentCoreArgumentException("String is null or empty");
        }
        return instance;
    }
}
