package com.github.he305.contentcore.account.infra.repository;

import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.model.enums.Role;
import com.github.he305.contentcore.account.domain.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountRepositoryMock implements AccountRepository {
    List<Account> accountList = new ArrayList<>(
            List.of(new Account(UUID.randomUUID(), "admin", "admin", Role.ADMIN))
    );

    @Override
    public void save(Account account) {
        accountList.add(account);
    }

    @Override
    public Optional<Account> findByName(String name) {
        return accountList.stream().filter(account -> account.getName().equals(name)).findAny();
    }

    @Override
    public Optional<Account> findByNameAndPassword(String name, String password) {
        return accountList.stream().filter(account -> account.getName().equals(name) && account.getPassword().equals(password)).findAny();
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return accountList.stream().filter(account -> account.getId().equals(id)).findAny();
    }
}
