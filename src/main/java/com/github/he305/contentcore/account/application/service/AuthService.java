package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.auth.User;
import com.github.he305.contentcore.account.application.commands.LoginAccountCommand;
import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;
import com.github.he305.contentcore.account.application.commands.RegisterServiceCommand;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.service.AccountService;
import com.github.he305.contentcore.configuration.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements RegisterAccountService, LoginAccountService, RegisterServiceService {
    private final AccountService accountService;
    private final TokenGenerator tokenGenerator;

    @Override
    public JwtResponseDto execute(LoginAccountCommand command) {
        Account account = accountService.login(command.getUsername(), command.getPassword());
        return authAndReturnTokenDto(account);
    }

    @Override
    public JwtResponseDto execute(RegisterAccountCommand command) {
        Account account = accountService.register(command.getUsername(), command.getPassword());
        return authAndReturnTokenDto(account);
    }

    @Override
    public JwtResponseDto execute(RegisterServiceCommand command) {
        Account account = accountService.registerService(command.getUsername(), command.getPassword());
        return authAndReturnTokenDto(account);
    }

    private JwtResponseDto authAndReturnTokenDto(Account account) {
        User user = User.create(account);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new JwtResponseDto(tokenGenerator.generateToken(authentication), tokenGenerator.generateRefreshToken(authentication));
    }

}
