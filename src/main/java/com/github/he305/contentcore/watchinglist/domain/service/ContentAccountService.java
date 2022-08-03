package com.github.he305.contentcore.watchinglist.domain.service;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;

public interface ContentAccountService {
    ContentAccountId getContentAccountId(ContentAccount account);

    ContentAccount getContentAccount(ContentAccountId id);
}
