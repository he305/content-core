package com.github.he305.contentcore.account.domain.exceptions;

public class AccountBadCredentialsException extends AccountLoginException {
    public AccountBadCredentialsException(String username) {
        super("Invalid password for user " + username);
    }
}
