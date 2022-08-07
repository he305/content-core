package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.auth.User;
import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;
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
public class RegisterAccountServiceImpl implements RegisterAccountService {

    private final AccountService accountService;
    private final TokenGenerator tokenGenerator;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Override
    public JwtResponseDto execute(RegisterAccountCommand command) {
        Account account = accountService.register(command.getUsername(), command.getPassword());
        User user = User.create(account);
        Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.authenticated(user, user.getPassword(), user.getAuthorities()));
        return new JwtResponseDto(tokenGenerator.createToken(authentication));
    }
}
