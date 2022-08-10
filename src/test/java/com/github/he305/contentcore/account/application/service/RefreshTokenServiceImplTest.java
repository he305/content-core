package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.dto.JwtRefreshTokenDto;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.application.exceptions.JwtRefreshTokenNotValidException;
import com.github.he305.contentcore.account.domain.exceptions.AccountNotFoundException;
import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.model.enums.Role;
import com.github.he305.contentcore.account.domain.service.AccountService;
import com.github.he305.contentcore.configuration.security.TokenGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceImplTest {

    @Mock
    private TokenGenerator tokenGenerator;
    @Mock
    private AccountService accountService;

    @InjectMocks
    private RefreshTokenServiceImpl underTest;

    @Test
    void refreshToken_tokenNotValid() {
        String token = "token";
        JwtRefreshTokenDto dto = new JwtRefreshTokenDto(token);
        Mockito.when(tokenGenerator.validateRefreshToken(token)).thenReturn(false);
        assertThrows(JwtRefreshTokenNotValidException.class, () ->
                underTest.refreshToken(dto));
    }

    @Test
    void refreshToken_userNotFound() {
        String token = "token";
        String userId = UUID.randomUUID().toString();
        JwtRefreshTokenDto dto = new JwtRefreshTokenDto(token);
        Mockito.when(tokenGenerator.validateRefreshToken(token)).thenReturn(true);
        Mockito.when(tokenGenerator.getRefreshTokenUsername(token)).thenReturn(userId);
        Mockito.when(accountService.loginById(UUID.fromString(userId))).thenThrow(AccountNotFoundException.class);
        assertThrows(JwtRefreshTokenNotValidException.class, () ->
                underTest.refreshToken(dto));
    }

    @Test
    void refreshToken_valid() {
        String token = "token";
        String userId = UUID.randomUUID().toString();
        Account account = new Account(UUID.randomUUID(), "name", "pass", Role.SERVICE);
        JwtRefreshTokenDto dto = new JwtRefreshTokenDto(token);
        Mockito.when(tokenGenerator.validateRefreshToken(token)).thenReturn(true);
        Mockito.when(tokenGenerator.getRefreshTokenUsername(token)).thenReturn(userId);
        Mockito.when(accountService.loginById(UUID.fromString(userId))).thenReturn(account);
        Mockito.when(tokenGenerator.generateToken(Mockito.any())).thenReturn("token");
        Mockito.when(tokenGenerator.generateRefreshToken(Mockito.any())).thenReturn("refresh");
        JwtResponseDto expected = new JwtResponseDto("token", "refresh");

        JwtResponseDto actual = underTest.refreshToken(dto);
        assertEquals(expected, actual);
    }
}