package com.github.he305.contentcore.account.application.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class JwtResponseDto {
    private final String type = "Bearer";
    private final String token;
}
