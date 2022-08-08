package com.github.he305.contentcore.account.infra.mapper;

import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.infra.data.AccountData;

public interface AccountDataMapper {
    AccountData toJpa(Account account);

    Account toDomain(AccountData jpa);
}
