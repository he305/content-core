package com.github.he305.contentcore.account.domain.service;

import com.github.he305.contentcore.account.domain.exceptions.AccountAlreadyExistsException;
import com.github.he305.contentcore.account.domain.exceptions.AccountBadCredentialsException;
import com.github.he305.contentcore.account.domain.exceptions.AccountNotFoundException;
import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.model.enums.Role;
import com.github.he305.contentcore.account.domain.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AccountServiceImpl underTest;

    @Test
    void register_accountAlreadyExists() {
        String username = "user";
        String password = "pass";
        Account account = new Account(UUID.randomUUID(), username, password, Role.ADMIN);
        Mockito.when(accountRepository.findByUsername(username)).thenReturn(Optional.of(account));
        assertThrows(AccountAlreadyExistsException.class, () ->
                underTest.register(username, password));
    }

    @Test
    void register_valid() {
        String username = "user";
        String password = "pass";
        Mockito.when(accountRepository.findByUsername(username)).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);

        Account actual = underTest.register(username, password);
        Account expected = new Account(actual.getId(), username, password, Role.USER);
        assertEquals(expected, actual);
    }

    @Test
    void registerService_accountAlreadyExists() {
        String username = "user";
        String password = "pass";
        Account account = new Account(UUID.randomUUID(), username, password, Role.ADMIN);
        Mockito.when(accountRepository.findByUsername(username)).thenReturn(Optional.of(account));
        assertThrows(AccountAlreadyExistsException.class, () ->
                underTest.registerService(username, password));
    }

    @Test
    void registerService_valid() {
        String username = "user";
        String password = "pass";
        Mockito.when(accountRepository.findByUsername(username)).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);

        Account actual = underTest.registerService(username, password);
        Account expected = new Account(actual.getId(), username, password, Role.SERVICE);
        assertEquals(expected, actual);
    }

    @Test
    void login_accountNotFoundException() {
        String username = "user";
        String password = "pass";
        Mockito.when(accountRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () ->
                underTest.login(username, password));
    }

    @Test
    void login_passwordsDonotMatch() {
        String username = "user";
        String password = "pass";
        Account account = new Account(UUID.randomUUID(), username, password, Role.ADMIN);
        Mockito.when(accountRepository.findByUsername(username)).thenReturn(Optional.of(account));
        Mockito.when(passwordEncoder.matches(password, password)).thenReturn(false);

        assertThrows(AccountBadCredentialsException.class, () ->
                underTest.login(username, password));
    }

    @Test
    void login_valid() {
        String username = "user";
        String password = "pass";
        Account account = new Account(UUID.randomUUID(), username, password, Role.ADMIN);
        Mockito.when(accountRepository.findByUsername(username)).thenReturn(Optional.of(account));
        Mockito.when(passwordEncoder.matches(password, password)).thenReturn(true);

        Account expected = new Account(account.getId(), username, password, Role.ADMIN);
        Account actual = underTest.login(username, password);
        assertEquals(expected, actual);
    }

    @Test
    void loginByUsername_notFound() {
        String username = "user";
        Mockito.when(accountRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () ->
                underTest.loginByUsername(username));
    }

    @Test
    void loginByUsername_valid() {
        String username = "user";
        Account account = new Account(UUID.randomUUID(), username, "pass", Role.ADMIN);
        Mockito.when(accountRepository.findByUsername(username)).thenReturn(Optional.of(account));

        Account actual = underTest.loginByUsername(username);
        assertEquals(account, actual);
    }

    @Test
    void loginById_notFound() {
        UUID id = UUID.randomUUID();
        Mockito.when(accountRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () ->
                underTest.loginById(id));
    }

    @Test
    void loginById_valid() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "user", "pass", Role.ADMIN);
        Mockito.when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        Account actual = underTest.loginById(id);
        assertEquals(account, actual);
    }
}