package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.auth.User;
import com.github.he305.contentcore.account.application.dto.JwtRefreshTokenDto;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.application.exceptions.JwtRefreshTokenNotValidException;
import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.service.AccountService;
import com.github.he305.contentcore.configuration.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final TokenGenerator tokenGenerator;
    private final AccountService accountService;

    @Override
    public JwtResponseDto refreshToken(JwtRefreshTokenDto dto) {
        try {
            if (!tokenGenerator.validateRefreshToken(dto.getRefreshToken())) {
                throw new JwtRefreshTokenNotValidException(dto.getRefreshToken());
            }

            String userId = tokenGenerator.getRefreshTokenUsername(dto.getRefreshToken());
            Account account = accountService.loginById(UUID.fromString(userId));
            User user = User.create(account);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
            return new JwtResponseDto(tokenGenerator.generateToken(authentication), tokenGenerator.generateRefreshToken(authentication));
        } catch (RuntimeException ex) {
            throw new JwtRefreshTokenNotValidException(dto.getRefreshToken());
        }
    }
}
