package com.github.he305.contentcore.contentaccount.infra.mapper;

import com.github.he305.contentcore.contentaccount.domain.model.ContentAccount;
import com.github.he305.contentcore.contentaccount.infra.data.ContentAccountData;

public interface ContentAccountDataMapper {
    ContentAccount toDomain(ContentAccountData data);

    ContentAccountData toJpa(ContentAccount account);
}
