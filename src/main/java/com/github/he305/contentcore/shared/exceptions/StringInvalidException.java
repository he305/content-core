package com.github.he305.contentcore.shared.exceptions;

public class StringInvalidException extends ContentCoreException {

    public StringInvalidException() {
        super("Value is invalid or null");
    }
}
