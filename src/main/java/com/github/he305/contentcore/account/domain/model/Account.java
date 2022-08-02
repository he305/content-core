package com.github.he305.contentcore.account.domain.model;

import com.github.he305.contentcore.account.domain.model.values.AccountProperties;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.UUID;

public class Account extends AbstractAggregateRoot<Account> {
    @Getter
    private final UUID id;
    private final AccountProperties accountProperties;

    public Account(UUID id, String name, String password) {
        this.accountProperties = new AccountProperties(name, password);
        this.id = id;
    }

    public static Account register(String name, String password) {
        return new Account(UUID.randomUUID(), name, password);
    }

    public String getName() {
        return accountProperties.getName();
    }

    public String getPassword() {
        return accountProperties.getPassword();
    }
}
