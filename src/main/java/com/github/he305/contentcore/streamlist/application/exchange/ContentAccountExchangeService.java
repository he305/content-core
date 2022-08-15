package com.github.he305.contentcore.streamlist.application.exchange;

import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;

import java.util.UUID;

public interface ContentAccountExchangeService {
    ContentAccountDetails getContentAccount(UUID contentAccountId);
}
