package com.github.he305.contentcore.account.domain.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class AccountAlreadyExistsException extends ContentCoreException {
    public AccountAlreadyExistsException() {
        super("Account already exists");
    }
}
