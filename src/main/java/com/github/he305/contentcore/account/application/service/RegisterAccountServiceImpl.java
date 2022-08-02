package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;
import com.github.he305.contentcore.account.domain.exceptions.AccountAlreadyExistsException;
import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterAccountServiceImpl implements RegisterAccountService {
    private final AccountRepository accountRepository;

    @Override
    public void register(RegisterAccountCommand command) {
        if (accountRepository.findByName(command.getName()).isPresent()) {
            throw new AccountAlreadyExistsException();
        }

        Account account = Account.register(command.getName(), command.getPassword());
        accountRepository.create(account);
    }
}
