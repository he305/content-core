package com.github.he305.contentcore.configuration.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenGenerator {

    @Value("${jwt.refresh.key}")
    public String signingKeyRefresh;
    @Value("${jwt.expiration-min}")
    private int expirationTokenMinutes;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret.key}")
    public String signingKeyPlain;
    @Value("${jwt.refresh.expiration-days}")
    private int expirationRefreshTokenDays;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signingKeyPlain);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Key getSigningKeyRefresh() {
        byte[] keyBytes = Decoders.BASE64URL.decode(signingKeyRefresh);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Instant time = Instant.now();
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(authentication.getName())
                .claim("role", authorities)
                .setIssuedAt(Date.from(time))
                .setExpiration(Date.from(time.plus(expirationTokenMinutes, ChronoUnit.MINUTES)))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final UserDetails userDetails) {

        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(getSigningKey()).build();

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("role").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKeyRefresh())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public String generateRefreshToken(Authentication authentication) {
        Instant time = Instant.now();
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(authentication.getName())
                .setIssuedAt(Date.from(time))
                .setExpiration(Date.from(time.plus(expirationRefreshTokenDays, ChronoUnit.DAYS)))
                .signWith(getSigningKeyRefresh())
                .compact();
    }

    public String getRefreshTokenUsername(String refreshToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKeyRefresh())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody()
                .getSubject();
    }
}
