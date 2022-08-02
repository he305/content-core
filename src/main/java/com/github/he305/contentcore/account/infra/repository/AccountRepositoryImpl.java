package com.github.he305.contentcore.account.infra.repository;

import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    List<Account> accountList = new ArrayList<>();

    @Override
    public void create(Account account) {
        accountList.add(account);
    }

    @Override
    public Optional<Account> findByName(String name) {
        return accountList.stream().filter(account -> account.getName().equals(name)).findAny();
    }
}
