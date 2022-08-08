package com.github.he305.contentcore.account.infra.repository;

import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.repository.AccountRepository;
import com.github.he305.contentcore.account.infra.data.AccountData;
import com.github.he305.contentcore.account.infra.jpa.JpaAccountRepository;
import com.github.he305.contentcore.account.infra.mapper.AccountDataMapper;
import com.github.he305.contentcore.shared.events.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final JpaAccountRepository jpaAccountRepository;
    private final AccountDataMapper accountDataMapper;
    private final EventPublisher publisher;

    @Override
    public void save(Account account) {
        AccountData jpa = accountDataMapper.toJpa(account);
        jpaAccountRepository.save(jpa);
        publisher.publishEvent(account.getEvents());
    }

    @Override
    public Optional<Account> findByUsername(String name) {
        Optional<AccountData> jpa = jpaAccountRepository.findByUsername(name);
        if (jpa.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(accountDataMapper.toDomain(jpa.get()));
    }

    @Override
    public Optional<Account> findByUsernameAndPassword(String name, String password) {
        Optional<AccountData> jpa = jpaAccountRepository.findByUsernameAndPassword(name, password);
        if (jpa.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(accountDataMapper.toDomain(jpa.get()));
    }

    @Override
    public Optional<Account> findById(UUID id) {
        // TODO: fix find by id in other repos
        Optional<AccountData> data = jpaAccountRepository.findById(id);
        if (data.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(accountDataMapper.toDomain(data.get()));
    }
}
