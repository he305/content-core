package com.github.he305.contentcore.account.domain.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class AccountLoginException extends ContentCoreException {
    public AccountLoginException(String message) {
        super(message);
    }
}
