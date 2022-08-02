package com.github.he305.contentcore.account.domain.repository;

import com.github.he305.contentcore.account.domain.model.Account;

import java.util.Optional;

public interface AccountRepository {
    void create(Account account);

    Optional<Account> findByName(String name);
}
