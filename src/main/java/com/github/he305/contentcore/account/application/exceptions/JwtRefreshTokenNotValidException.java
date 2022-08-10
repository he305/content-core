package com.github.he305.contentcore.account.application.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class JwtRefreshTokenNotValidException extends ContentCoreException {
    public JwtRefreshTokenNotValidException(String token) {
        super(token + " is not a valid refresh token");
    }
}
