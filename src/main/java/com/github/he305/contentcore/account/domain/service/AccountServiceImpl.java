package com.github.he305.contentcore.account.domain.service;

import com.github.he305.contentcore.account.domain.exceptions.AccountAlreadyExistsException;
import com.github.he305.contentcore.account.domain.exceptions.AccountBadCredentialsException;
import com.github.he305.contentcore.account.domain.exceptions.AccountLoginException;
import com.github.he305.contentcore.account.domain.exceptions.AccountNotFoundException;
import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Account register(String username, String password) throws AccountAlreadyExistsException {
        if (accountRepository.findByUsername(username).isPresent()) {
            throw new AccountAlreadyExistsException();
        }
        Account account = Account.register(username, passwordEncoder.encode(password));
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account login(String username, String password) throws AccountLoginException {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isEmpty()) {
            throw new AccountNotFoundException(username);
        }

        if (passwordEncoder.matches(password, account.get().getPassword())) {
            return account.get();
        } else {
            throw new AccountBadCredentialsException(username);
        }
    }

    @Override
    public Account loginByUsername(String username) throws AccountNotFoundException {
        return accountRepository.findByUsername(username).orElseThrow(() -> new AccountNotFoundException(username));
    }

    @Override
    public Account loginById(UUID id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id.toString()));
    }
}
