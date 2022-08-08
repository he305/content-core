package com.github.he305.contentcore.account.infra.mapper;

import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.model.enums.Role;
import com.github.he305.contentcore.account.infra.data.AccountData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountDataMapperImplTest {

    private AccountDataMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new AccountDataMapperImpl();
    }

    @Test
    void toJpa() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "user", "pass", Role.ADMIN);
        AccountData expected = new AccountData(id, "user", "pass", Role.ADMIN);
        AccountData actual = underTest.toJpa(account);
        assertEquals(expected, actual);
    }

    @Test
    void toDomain() {
        UUID id = UUID.randomUUID();
        AccountData data = new AccountData(id, "user", "pass", Role.ADMIN);
        Account expected = new Account(id, "user", "pass", Role.ADMIN);
        Account actual = underTest.toDomain(data);
        assertEquals(expected, actual);
    }
}