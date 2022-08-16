package com.github.he305.contentcore.watchinglist.application.exchange;

import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;

import java.util.UUID;

public interface WatchingListContentAccountExchangeService {
    UUID getContentAccountId(WatchingListContentAccount account);

    ContentAccountDetails getContentAccount(UUID id);
}
