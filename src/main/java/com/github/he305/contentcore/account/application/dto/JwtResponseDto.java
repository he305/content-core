package com.github.he305.contentcore.account.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class JwtResponseDto {
    private final String type = "Bearer";
    private String token;
}
