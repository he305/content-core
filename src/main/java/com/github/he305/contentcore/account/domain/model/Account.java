package com.github.he305.contentcore.account.domain.model;

import com.github.he305.contentcore.account.domain.model.enums.Role;
import com.github.he305.contentcore.account.domain.model.values.AccountProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Account extends AbstractAggregateRoot<Account> {
    private final UUID id;
    private final AccountProperties accountProperties;

    private final Role role;


    public Account(UUID id, String name, String password, Role role) {
        this.accountProperties = new AccountProperties(name, password);
        this.id = id;
        this.role = role;
    }

    public static Account register(String name, String password) {
        return new Account(UUID.randomUUID(), name, password, Role.USER);
    }

    public static Account registerService(String name, String password) {
        return new Account(UUID.randomUUID(), name, password, Role.SERVICE);
    }

    public String getUsername() {
        return accountProperties.getUsername();
    }

    public String getPassword() {
        return accountProperties.getPassword();
    }

    public Collection<Object> getEvents() {
        return domainEvents();
    }
}
