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
import com.github.he305.contentcore.shared.exceptions.ContentCoreException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertThrows(ContentCoreException.class, () -> underTest.login(dto));
    }

    @Test
    void login_valid() {
        LoginRequestDto dto = new LoginRequestDto("user", "pass");
        LoginAccountCommand command = new LoginAccountCommand("user", "pass");
        JwtResponseDto res = new JwtResponseDto("test", "refresh");
        Mockito.when(loginAccountService.execute(command)).thenReturn(res);

        JwtResponseDto actual = underTest.login(dto);
        assertEquals(res, actual);
    }

    @Test
    void register_alreadyExistsException() {
        LoginRequestDto dto = new LoginRequestDto("user", "pass");
        RegisterAccountCommand command = new RegisterAccountCommand("user", "pass");
        Mockito.when(registerAccountService.execute(command)).thenThrow(AccountAlreadyExistsException.class);

        assertThrows(ContentCoreException.class, () -> underTest.register(dto));
    }


    @Test
    void register_blankPassword() {
        LoginRequestDto dto = new LoginRequestDto("asd", "");


        assertThrows(ContentCoreException.class, () -> underTest.register(dto));
    }

    @Test
    void register_valid() {
        LoginRequestDto dto = new LoginRequestDto("user", "pass");
        RegisterAccountCommand command = new RegisterAccountCommand("user", "pass");
        JwtResponseDto res = new JwtResponseDto("test", "refresh");
        Mockito.when(registerAccountService.execute(command)).thenReturn(res);

        JwtResponseDto actual = underTest.register(dto);
        assertEquals(res, actual);
    }

    @Test
    void registerService_notEqualSecretKey() {
        ReflectionTestUtils.setField(underTest, "serviceRegisterKey", "test");

        RegisterServiceDto dto = new RegisterServiceDto("user", "pass", "1");
        assertThrows(ContentCoreException.class, () -> underTest.registerService(dto));
    }

    @Test
    void registerService_alreadyExistsException() {
        ReflectionTestUtils.setField(underTest, "serviceRegisterKey", "test");
        RegisterServiceDto dto = new RegisterServiceDto("user", "pass", "test");
        RegisterServiceCommand command = new RegisterServiceCommand("user", "pass");
        Mockito.when(registerServiceService.execute(command)).thenThrow(AccountAlreadyExistsException.class);

        assertThrows(ContentCoreException.class, () -> underTest.registerService(dto));
    }

    @Test
    void registerService_valid() {
        ReflectionTestUtils.setField(underTest, "serviceRegisterKey", "test");
        RegisterServiceDto dto = new RegisterServiceDto("user", "pass", "test");
        RegisterServiceCommand command = new RegisterServiceCommand("user", "pass");
        JwtResponseDto res = new JwtResponseDto("test", "refresh");
        Mockito.when(registerServiceService.execute(command)).thenReturn(res);

        JwtResponseDto actual = underTest.registerService(dto);
        assertEquals(res, actual);
    }

    @Test
    void refreshToken_notValid() {
        JwtRefreshTokenDto dto = new JwtRefreshTokenDto("token");
        Mockito.when(refreshTokenService.refreshToken(dto)).thenThrow(JwtRefreshTokenNotValidException.class);

        assertThrows(ContentCoreException.class, () -> underTest.refreshToken(dto));
    }

    @Test
    void refreshToken_valid() {
        JwtRefreshTokenDto dto = new JwtRefreshTokenDto("token");
        JwtResponseDto res = new JwtResponseDto("token", "refresh");
        Mockito.when(refreshTokenService.refreshToken(dto)).thenReturn(res);

        JwtResponseDto actual = underTest.refreshToken(dto);
        assertEquals(res, actual);
    }
}