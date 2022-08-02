package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;
import com.github.he305.contentcore.account.domain.exceptions.AccountAlreadyExistsException;
import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterAccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    private RegisterAccountServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new RegisterAccountServiceImpl(accountRepository);
    }

    @Test
    void register_success() {
        RegisterAccountCommand command = new RegisterAccountCommand(
                "name",
                "password"
        );

        when(accountRepository.findByName(command.getName())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> underTest.register(command));
    }

    @Test
    void register_existingAccount() {
        RegisterAccountCommand command = new RegisterAccountCommand(
                "name",
                "password"
        );

        when(accountRepository.findByName(command.getName())).thenReturn(Optional.of(new Account(UUID.randomUUID(), command.getName(), command.getPassword())));
        assertThrows(AccountAlreadyExistsException.class, () ->
                underTest.register(command));
    }
}