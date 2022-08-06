package com.github.he305.contentcore.contentaccount.domain.repository;

import com.github.he305.contentcore.contentaccount.domain.model.ContentAccount;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;

import java.util.Optional;
import java.util.UUID;

public interface ContentAccountRepository {
    void save(ContentAccount contentAccount);

    ContentAccount getById(UUID id);

    Optional<ContentAccount> getByContentAccountDetails(ContentAccountDetails details);
}
