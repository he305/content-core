package com.github.he305.contentcore.configuration.security;

import com.github.he305.contentcore.account.application.auth.User;
import com.github.he305.contentcore.account.domain.model.enums.Role;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenGeneratorTest {

    private TokenGenerator underTest;

    @BeforeEach
    void setUp() {
        underTest = new TokenGenerator();
    }

    private void prepareValues() {
        ReflectionTestUtils.setField(underTest, "signingKeyRefresh", "YmJsYWJsYWxiYWxzYWRsYWRxd2Vybmp3ZXVibmFzbmRhaWJuIGRpd3FuZWRrelNtZHBhRE9TYWFzZG5hc2RmbnVpYnF3aXVxIGVxZHFxZGRhc2Q=");
        ReflectionTestUtils.setField(underTest, "signingKeyPlain", "dGhpcyBpcyBteSB2ZXJ5IHNlY3JldCBrZXkgdGhhdCBub2JvZHkga25vd3MgYWJvdXQuIGkgYW0gc3VyZSB0aGF0IG5vYm9keSB3aWxsIGV2ZXIga25vdyBhYm91dCB0aGUgY29udGVudHMgb2YgdGhpcyBrZXkuIGkgaG9wZSB0aGlzIGlzIGVub3VnaCBmb3IgMjU2IGJpdHM=");
        ReflectionTestUtils.setField(underTest, "expirationTokenMinutes", 0);
        ReflectionTestUtils.setField(underTest, "issuer", "test");
        ReflectionTestUtils.setField(underTest, "expirationRefreshTokenDays", 0);

    }

    @Test
    void generateToken_valid() {
        prepareValues();
        User user = new User(UUID.randomUUID(), "pass", Role.USER);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        assertDoesNotThrow(() -> underTest.generateToken(authentication));
    }

    @Test
    void getUsernameFromToken_timeExpired() {
        prepareValues();
        User user = new User(UUID.randomUUID(), "pass", Role.USER);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        String token = underTest.generateToken(authentication);

        assertThrows(ExpiredJwtException.class, () -> underTest.getUsernameFromToken(token));
    }
}