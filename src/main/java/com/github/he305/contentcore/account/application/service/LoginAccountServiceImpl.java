package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.auth.User;
import com.github.he305.contentcore.account.application.commands.LoginAccountCommand;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.service.AccountService;
import com.github.he305.contentcore.configuration.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginAccountServiceImpl implements LoginAccountService {

    private final AccountService accountService;
    private final TokenGenerator tokenGenerator;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Override
    public JwtResponseDto execute(LoginAccountCommand command) {
        Account account = accountService.login(command.getUsername(), command.getPassword());
        User user = User.create(account);
        Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.authenticated(user, user.getPassword(), user.getAuthorities()));
        return new JwtResponseDto(tokenGenerator.createToken(authentication));
    }
}
