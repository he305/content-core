package com.github.he305.contentcore.watchinglist.application.exchange;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.service.ContentAccountExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentAccountExchangeServiceImpl implements ContentAccountExchangeService {
    private final com.github.he305.contentcore.contentaccount.application.exchange.ContentAccountExchangeService contentAccountExchangeService;

    @Override
    public ContentAccountId getContentAccountId(ContentAccount account) {
        return contentAccountExchangeService.getContentAccountId(account);
    }

    @Override
    public ContentAccount getContentAccount(ContentAccountId id) {
        return contentAccountExchangeService.getContentAccountById(id.getId());
    }
}
