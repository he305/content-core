package com.github.he305.contentcore.account.application.controller;

import com.github.he305.contentcore.account.application.commands.LoginAccountCommand;
import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.application.dto.LoginRequestDto;
import com.github.he305.contentcore.account.application.service.LoginAccountService;
import com.github.he305.contentcore.account.application.service.RegisterAccountService;
import com.github.he305.contentcore.account.domain.exceptions.AccountAlreadyExistsException;
import com.github.he305.contentcore.account.domain.exceptions.AccountLoginException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private RegisterAccountService registerAccountService;
    @Mock
    private LoginAccountService loginAccountService;

    @InjectMocks
    private AuthController underTest;

    @Test
    void login_loginException() {
        LoginRequestDto dto = new LoginRequestDto("user", "pass");
        LoginAccountCommand command = new LoginAccountCommand("user", "pass");
        Mockito.when(loginAccountService.execute(command)).thenThrow(AccountLoginException.class);
        ResponseEntity<JwtResponseDto> expected = ResponseEntity.badRequest().build();

        ResponseEntity<JwtResponseDto> actual = underTest.login(dto);
        assertEquals(expected, actual);
    }

    @Test
    void login_valid() {
        LoginRequestDto dto = new LoginRequestDto("user", "pass");
        LoginAccountCommand command = new LoginAccountCommand("user", "pass");
        JwtResponseDto res = new JwtResponseDto("test");
        Mockito.when(loginAccountService.execute(command)).thenReturn(res);
        ResponseEntity<JwtResponseDto> expected = ResponseEntity.ok(res);

        ResponseEntity<JwtResponseDto> actual = underTest.login(dto);
        assertEquals(expected, actual);
    }

    @Test
    void register_alreadyExistsException() {
        LoginRequestDto dto = new LoginRequestDto("user", "pass");
        RegisterAccountCommand command = new RegisterAccountCommand("user", "pass");
        Mockito.when(registerAccountService.execute(command)).thenThrow(AccountAlreadyExistsException.class);
        ResponseEntity<JwtResponseDto> expected = ResponseEntity.badRequest().build();

        ResponseEntity<JwtResponseDto> actual = underTest.register(dto);
        assertEquals(expected, actual);
    }

    @Test
    void register_valid() {
        LoginRequestDto dto = new LoginRequestDto("user", "pass");
        RegisterAccountCommand command = new RegisterAccountCommand("user", "pass");
        JwtResponseDto res = new JwtResponseDto("test");
        Mockito.when(registerAccountService.execute(command)).thenReturn(res);
        ResponseEntity<JwtResponseDto> expected = ResponseEntity.ok(res);

        ResponseEntity<JwtResponseDto> actual = underTest.register(dto);
        assertEquals(expected, actual);
    }
}