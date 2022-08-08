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
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AuthServiceTest {
    @MockBean
    private AccountService accountService;
    @MockBean
    private TokenGenerator tokenGenerator;

    private AuthService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AuthService(accountService, tokenGenerator);
    }

    @Test
    void testLogin() {
        LoginAccountCommand command = new LoginAccountCommand("user", "pass");
        JwtResponseDto expected = new JwtResponseDto("test");
        Mockito.when(accountService.login("user", "pass")).thenReturn(new Account(UUID.randomUUID(), "user", "pass", Role.ADMIN));
        Mockito.when(tokenGenerator.generateToken(Mockito.any())).thenReturn("test");

        JwtResponseDto actual = underTest.execute(command);
        assertEquals(expected.getToken(), actual.getToken());
    }

    @Test
    void testRegister() {
        RegisterAccountCommand command = new RegisterAccountCommand("user", "pass");
        JwtResponseDto expected = new JwtResponseDto("test");
        Mockito.when(accountService.register("user", "pass")).thenReturn(new Account(UUID.randomUUID(), "user", "pass", Role.ADMIN));
        Mockito.when(tokenGenerator.generateToken(Mockito.any())).thenReturn("test");

        JwtResponseDto actual = underTest.execute(command);
        assertEquals(expected.getToken(), actual.getToken());
    }

    @Test
    void testRegisterService() {
        RegisterServiceCommand command = new RegisterServiceCommand("user", "pass");
        JwtResponseDto expected = new JwtResponseDto("test");
        Mockito.when(accountService.registerService("user", "pass")).thenReturn(new Account(UUID.randomUUID(), "user", "pass", Role.ADMIN));
        Mockito.when(tokenGenerator.generateToken(Mockito.any())).thenReturn("test");

        JwtResponseDto actual = underTest.execute(command);
        assertEquals(expected.getToken(), actual.getToken());
    }
}