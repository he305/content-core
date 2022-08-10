package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.dto.JwtRefreshTokenDto;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;

public interface RefreshTokenService {
    JwtResponseDto refreshToken(JwtRefreshTokenDto dto);
}
