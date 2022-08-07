package com.github.he305.contentcore.account.domain.repository;

import com.github.he305.contentcore.account.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    void save(Account account);

    Optional<Account> findByName(String name);

    Optional<Account> findByNameAndPassword(String name, String password);

    Optional<Account> findById(UUID id);
}
