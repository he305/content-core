package com.github.he305.contentcore.watchinglist.application.exchange;

import com.github.he305.contentcore.contentaccount.application.exchange.ContentAccountExchangeService;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WatchingListContentAccountExchangeServiceImpl implements WatchingListContentAccountExchangeService {
    private final ContentAccountExchangeService contentAccountExchangeService;

    @Override
    public UUID getContentAccountId(WatchingListContentAccount account) {
        return contentAccountExchangeService.getContentAccountId(account);
    }

    @Override
    public ContentAccountDetails getContentAccount(UUID id) {
        return  contentAccountExchangeService.getContentAccountById(id);
    }
}
