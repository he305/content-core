package com.github.he305.contentcore.account.domain.repository;

import com.github.he305.contentcore.account.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    void save(Account account);

    Optional<Account> findByUsername(String name);

    Optional<Account> findByUsernameAndPassword(String name, String password);

    Optional<Account> findById(UUID id);
}
