package com.github.he305.contentcore.account.infra.mapper;

import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.infra.data.AccountData;
import org.springframework.stereotype.Component;

@Component
public class AccountDataMapperImpl implements AccountDataMapper {
    @Override
    public AccountData toJpa(Account account) {
        return new AccountData(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                account.getRole()
        );
    }

    @Override
    public Account toDomain(AccountData jpa) {
        return new Account(
                jpa.getId(),
                jpa.getUsername(),
                jpa.getPassword(),
                jpa.getRole()
        );
    }
}
