package com.github.he305.contentcore.configuration.security;

import com.github.he305.contentcore.account.application.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Component
public class TokenGenerator {
    @Autowired
    JwtEncoder accessTokenEncoder;

    @Value("${jwt.expiration-min}")
    private int expirationMinutes;

    @Value("${jwt.issuer}")
    private String issuer;

    public String createToken(Authentication authentication) {
        validateAuthenticationPrincipal(authentication);
        return createAccessToken(authentication);
    }

    private void validateAuthenticationPrincipal(Authentication authentication) {
        Object obj = authentication.getPrincipal();
        if (!(obj instanceof User)) {
            throw new BadCredentialsException(
                    MessageFormat.format("principal {0} is not of User type", authentication.getPrincipal().getClass())
            );
        }
    }

    private String createAccessToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Instant now = Instant.now();

        String claims = authentication.getAuthorities()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plus(expirationMinutes, ChronoUnit.MINUTES))
                .subject(user.getUsername())
                .claim("id", user.getId().toString())
                .claim("scope", claims)
                .build();

        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

//    public User getUserFromToken(String token) {
//        Jwts.parserBuilder().setSigningKey()
//    }
//}
}
