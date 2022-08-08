package com.github.he305.contentcore.account.infra.repository;

import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.model.enums.Role;
import com.github.he305.contentcore.account.infra.data.AccountData;
import com.github.he305.contentcore.account.infra.jpa.JpaAccountRepository;
import com.github.he305.contentcore.account.infra.mapper.AccountDataMapper;
import com.github.he305.contentcore.shared.events.EventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryImplTest {

    @Mock
    private JpaAccountRepository jpaAccountRepository;
    @Mock
    private AccountDataMapper accountDataMapper;
    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private AccountRepositoryImpl underTest;

    @Test
    void save() {
        Account account = Account.register("name", "pass");
        Mockito.when(accountDataMapper.toJpa(account)).thenReturn(new AccountData());
        assertDoesNotThrow(() -> underTest.save(account));
    }

    @Test
    void findByUsername_empty() {
        String username = "user";
        Mockito.when(jpaAccountRepository.findByUsername(username)).thenReturn(Optional.empty());
        Optional<Account> expected = Optional.empty();
        Optional<Account> actual = underTest.findByUsername(username);
        assertEquals(expected, actual);
    }

    @Test
    void findByUsername_data() {
        String username = "user";
        AccountData accountData = new AccountData();
        Account account = new Account(UUID.randomUUID(), "user", "pass", Role.ADMIN);
        Mockito.when(jpaAccountRepository.findByUsername(username)).thenReturn(Optional.of(accountData));
        Mockito.when(accountDataMapper.toDomain(accountData)).thenReturn(account);

        Optional<Account> actual = underTest.findByUsername(username);
        assertTrue(actual.isPresent());
        assertEquals(account, actual.get());
    }

    @Test
    void findByUsernameAndPassword_empty() {
        String username = "user";
        String password = "pass";
        Mockito.when(jpaAccountRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());
        Optional<Account> expected = Optional.empty();
        Optional<Account> actual = underTest.findByUsernameAndPassword(username, password);
        assertEquals(expected, actual);
    }

    @Test
    void findByUsernameAndPassword_data() {
        String username = "user";
        String password = "pass";
        AccountData accountData = new AccountData();
        Account account = new Account(UUID.randomUUID(), "user", "pass", Role.ADMIN);
        Mockito.when(jpaAccountRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(accountData));
        Mockito.when(accountDataMapper.toDomain(accountData)).thenReturn(account);

        Optional<Account> actual = underTest.findByUsernameAndPassword(username, password);
        assertTrue(actual.isPresent());
        assertEquals(account, actual.get());
    }

    @Test
    void findById_empty() {
        UUID id = UUID.randomUUID();
        Mockito.when(jpaAccountRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Account> expected = Optional.empty();
        Optional<Account> actual = underTest.findById(id);
        assertEquals(expected, actual);
    }

    @Test
    void findById_data() {
        UUID id = UUID.randomUUID();
        AccountData accountData = new AccountData();
        Account account = new Account(UUID.randomUUID(), "user", "pass", Role.ADMIN);
        Mockito.when(jpaAccountRepository.findById(id)).thenReturn(Optional.of(accountData));
        Mockito.when(accountDataMapper.toDomain(accountData)).thenReturn(account);

        Optional<Account> actual = underTest.findById(id);
        assertTrue(actual.isPresent());
        assertEquals(account, actual.get());
    }
}