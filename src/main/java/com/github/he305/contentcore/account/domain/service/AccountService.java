package com.github.he305.contentcore.account.domain.service;

import com.github.he305.contentcore.account.domain.exceptions.AccountAlreadyExistsException;
import com.github.he305.contentcore.account.domain.exceptions.AccountLoginException;
import com.github.he305.contentcore.account.domain.exceptions.AccountNotFoundException;
import com.github.he305.contentcore.account.domain.model.Account;

import java.util.UUID;

public interface AccountService {
    Account register(String username, String password) throws AccountAlreadyExistsException;

    Account login(String username, String password) throws AccountLoginException;

    Account loginByUsername(String username) throws AccountNotFoundException;

    Account loginById(UUID id) throws AccountNotFoundException;
}
