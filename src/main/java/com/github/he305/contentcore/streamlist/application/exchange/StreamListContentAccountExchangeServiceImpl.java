package com.github.he305.contentcore.streamlist.application.exchange;

import com.github.he305.contentcore.contentaccount.application.exchange.ContentAccountExchangeService;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StreamListContentAccountExchangeServiceImpl implements StreamListContentAccountExchangeService {
    private final ContentAccountExchangeService contentAccountExchangeService;

    @Override
    public ContentAccountDetails getContentAccount(UUID contentAccountId) {
        ContentAccount contentAccount = contentAccountExchangeService.getContentAccountById(contentAccountId);
        return new ContentAccountDetails(contentAccount.getName(), Platform.valueOf(contentAccount.getPlatform().name()));
    }
}
