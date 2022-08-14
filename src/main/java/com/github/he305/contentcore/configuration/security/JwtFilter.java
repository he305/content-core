package com.github.he305.contentcore.configuration.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    public static final String TOKEN_TYPE = "Bearer ";
    private final UserDetailsService userDetailsService;

    private final TokenGenerator jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Optional<String> optToken = getAuthToken(req);
        if (optToken.isEmpty()) {
            chain.doFilter(req, res);
            return;
        }

        String token = optToken.get();
        Optional<String> optUsername = getUsernameFromToken(token);
        if (optUsername.isEmpty()) {
            chain.doFilter(req, res);
            return;
        }

        String username = optUsername.get();
        Optional<UserDetails> optionalUserDetails = getUserDetailsFromUsername(username);
        if (optionalUserDetails.isEmpty()) {
            chain.doFilter(req, res);
            return;
        }

        UserDetails userDetails = optionalUserDetails.get();
        if (jwtTokenUtil.validateToken(token, userDetails)) {
            UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(token, userDetails);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(req, res);
    }

    private Optional<String> getAuthToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(TOKEN_TYPE)) {
            return Optional.empty();
        }

        return Optional.of(header.replace(TOKEN_TYPE, ""));
    }

    private Optional<String> getUsernameFromToken(String token) {
        try {
            return Optional.ofNullable(jwtTokenUtil.getUsernameFromToken(token));
        } catch (IllegalArgumentException e) {
            logger.error("An error occurred while fetching Username from Token", e);
        } catch (ExpiredJwtException e) {
            logger.info("The token has expired");
        } catch (SignatureException e) {
            logger.error("Signature exception: " + e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<UserDetails> getUserDetailsFromUsername(String username) {
        try {
            return Optional.ofNullable(userDetailsService.loadUserByUsername(username));
        } catch (UsernameNotFoundException ex) {
            log.error(ex.getMessage());
        }
        return Optional.empty();
    }


}
