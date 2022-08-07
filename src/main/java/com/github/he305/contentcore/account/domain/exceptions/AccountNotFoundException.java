package com.github.he305.contentcore.account.domain.exceptions;

public class AccountNotFoundException extends AccountLoginException {
    public AccountNotFoundException(String username) {
        super("Account with name " + username + " doesn't exist");
    }
}
