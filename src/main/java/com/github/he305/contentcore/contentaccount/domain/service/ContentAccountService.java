package com.github.he305.contentcore.contentaccount.domain.service;

import com.github.he305.contentcore.contentaccount.domain.model.ContentAccount;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;

import java.util.UUID;

public interface ContentAccountService {
    ContentAccount getContentAccountOrCreate(ContentAccountDetails details);

    ContentAccountDetails getContentAccountDetailsById(UUID id);
}
