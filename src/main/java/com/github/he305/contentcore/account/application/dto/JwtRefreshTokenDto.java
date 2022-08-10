package com.github.he305.contentcore.account.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JwtRefreshTokenDto {
    private String refreshToken;
}
