package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.commands.LoginAccountCommand;
import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;
import com.github.he305.contentcore.account.application.commands.RegisterServiceCommand;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.model.enums.Role;
import com.github.he305.contentcore.account.domain.service.AccountService;
import com.github.he305.contentcore.configuration.security.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private AccountService accountService;
    @Mock
    private TokenGenerator tokenGenerator;

    private AuthService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AuthService(accountService, tokenGenerator);
    }

    @Test
    void testLogin() {
        LoginAccountCommand command = new LoginAccountCommand("user", "pass");
        JwtResponseDto expected = new JwtResponseDto("test", "refresh");
        Mockito.when(accountService.login("user", "pass")).thenReturn(new Account(UUID.randomUUID(), "user", "pass", Role.ADMIN));
        Mockito.when(tokenGenerator.generateToken(Mockito.any())).thenReturn("test");
        Mockito.when(tokenGenerator.generateRefreshToken(Mockito.any())).thenReturn("refresh");

        JwtResponseDto actual = underTest.execute(command);
        assertEquals(expected.getToken(), actual.getToken());
    }

    @Test
    void testRegister() {
        RegisterAccountCommand command = new RegisterAccountCommand("user", "pass");
        JwtResponseDto expected = new JwtResponseDto("test", "refresh");
        Mockito.when(accountService.register("user", "pass")).thenReturn(new Account(UUID.randomUUID(), "user", "pass", Role.ADMIN));
        Mockito.when(tokenGenerator.generateToken(Mockito.any())).thenReturn("test");
        Mockito.when(tokenGenerator.generateRefreshToken(Mockito.any())).thenReturn("refresh");

        JwtResponseDto actual = underTest.execute(command);
        assertEquals(expected.getToken(), actual.getToken());
    }

    @Test
    void testRegisterService() {
        RegisterServiceCommand command = new RegisterServiceCommand("user", "pass");
        JwtResponseDto expected = new JwtResponseDto("test", "refresh");
        Mockito.when(accountService.registerService("user", "pass")).thenReturn(new Account(UUID.randomUUID(), "user", "pass", Role.ADMIN));
        Mockito.when(tokenGenerator.generateToken(Mockito.any())).thenReturn("test");
        Mockito.when(tokenGenerator.generateRefreshToken(Mockito.any())).thenReturn("refresh");

        JwtResponseDto actual = underTest.execute(command);
        assertEquals(expected.getToken(), actual.getToken());
    }
}