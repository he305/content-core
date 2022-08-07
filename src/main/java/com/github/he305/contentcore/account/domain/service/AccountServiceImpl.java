package com.github.he305.contentcore.account.domain.service;

import com.github.he305.contentcore.account.domain.exceptions.AccountAlreadyExistsException;
import com.github.he305.contentcore.account.domain.exceptions.AccountLoginException;
import com.github.he305.contentcore.account.domain.exceptions.AccountNotFoundException;
import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account register(String username, String password) throws AccountAlreadyExistsException {
        if (accountRepository.findByName(username).isPresent()) {
            throw new AccountAlreadyExistsException();
        }
        Account account = Account.register(username, password);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account login(String username, String password) throws AccountLoginException {
        return accountRepository.findByNameAndPassword(username, password).orElseThrow(() -> new AccountLoginException("Can't login"));
    }

    @Override
    public Account loginByUsername(String username) throws AccountNotFoundException {
        return accountRepository.findByName(username).orElseThrow(() -> new AccountNotFoundException(username));
    }

    @Override
    public Account loginById(UUID id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id.toString()));
    }
}
