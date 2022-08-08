package com.github.he305.contentcore.account.application.auth;

import com.github.he305.contentcore.account.domain.exceptions.AccountNotFoundException;
import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Account account = accountService.loginById(UUID.fromString(username));
            return User.create(account);
        } catch (AccountNotFoundException ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
    }
}
