package com.github.he305.contentcore.account.application.controller;

import com.github.he305.contentcore.account.application.commands.LoginAccountCommand;
import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;
import com.github.he305.contentcore.account.application.commands.RegisterServiceCommand;
import com.github.he305.contentcore.account.application.dto.JwtRefreshTokenDto;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.application.dto.LoginRequestDto;
import com.github.he305.contentcore.account.application.dto.RegisterServiceDto;
import com.github.he305.contentcore.account.application.exceptions.JwtRefreshTokenNotValidException;
import com.github.he305.contentcore.account.application.service.LoginAccountService;
import com.github.he305.contentcore.account.application.service.RefreshTokenService;
import com.github.he305.contentcore.account.application.service.RegisterAccountService;
import com.github.he305.contentcore.account.application.service.RegisterServiceService;
import com.github.he305.contentcore.account.domain.exceptions.AccountAlreadyExistsException;
import com.github.he305.contentcore.account.domain.exceptions.AccountLoginException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private RegisterAccountService registerAccountService;
    @Mock
    private LoginAccountService loginAccountService;
    @Mock
    private RegisterServiceService registerServiceService;
    @Mock
    private RefreshTokenService refreshTokenService;

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
        JwtResponseDto res = new JwtResponseDto("test", "refresh");
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
        JwtResponseDto res = new JwtResponseDto("test", "refresh");
        Mockito.when(registerAccountService.execute(command)).thenReturn(res);
        ResponseEntity<JwtResponseDto> expected = ResponseEntity.ok(res);

        ResponseEntity<JwtResponseDto> actual = underTest.register(dto);
        assertEquals(expected, actual);
    }

    @Test
    void registerService_notEqualSecretKey() {
        ResponseEntity<JwtResponseDto> expected = ResponseEntity.badRequest().build();
        ReflectionTestUtils.setField(underTest, "serviceRegisterKey", "test");

        RegisterServiceDto dto = new RegisterServiceDto("user", "pass", "1");
        ResponseEntity<JwtResponseDto> actual = underTest.registerService(dto);
        assertEquals(expected, actual);
    }

    @Test
    void registerService_alreadyExistsException() {
        ReflectionTestUtils.setField(underTest, "serviceRegisterKey", "test");
        RegisterServiceDto dto = new RegisterServiceDto("user", "pass", "test");
        RegisterServiceCommand command = new RegisterServiceCommand("user", "pass");
        Mockito.when(registerServiceService.execute(command)).thenThrow(AccountAlreadyExistsException.class);
        ResponseEntity<JwtResponseDto> expected = ResponseEntity.badRequest().build();

        ResponseEntity<JwtResponseDto> actual = underTest.registerService(dto);
        assertEquals(expected, actual);
    }

    @Test
    void registerService_valid() {
        ReflectionTestUtils.setField(underTest, "serviceRegisterKey", "test");
        RegisterServiceDto dto = new RegisterServiceDto("user", "pass", "test");
        RegisterServiceCommand command = new RegisterServiceCommand("user", "pass");
        JwtResponseDto res = new JwtResponseDto("test", "refresh");
        Mockito.when(registerServiceService.execute(command)).thenReturn(res);
        ResponseEntity<JwtResponseDto> expected = ResponseEntity.ok(res);

        ResponseEntity<JwtResponseDto> actual = underTest.registerService(dto);
        assertEquals(expected, actual);
    }

    @Test
    void refreshToken_notValid() {
        JwtRefreshTokenDto dto = new JwtRefreshTokenDto("token");
        Mockito.when(refreshTokenService.refreshToken(dto)).thenThrow(JwtRefreshTokenNotValidException.class);
        ResponseEntity<JwtResponseDto> expected = ResponseEntity.badRequest().build();

        ResponseEntity<JwtResponseDto> actual = underTest.refreshToken(dto);
        assertEquals(expected, actual);
    }

    @Test
    void refreshToken_valid() {
        JwtRefreshTokenDto dto = new JwtRefreshTokenDto("token");
        JwtResponseDto res = new JwtResponseDto("token", "refresh");
        Mockito.when(refreshTokenService.refreshToken(dto)).thenReturn(res);
        ResponseEntity<JwtResponseDto> expected = ResponseEntity.ok(res);

        ResponseEntity<JwtResponseDto> actual = underTest.refreshToken(dto);
        assertEquals(expected, actual);
    }
}