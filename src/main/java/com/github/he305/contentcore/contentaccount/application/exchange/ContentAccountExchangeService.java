package com.github.he305.contentcore.contentaccount.application.exchange;

import com.github.he305.contentcore.contentaccount.application.query.GetContentAccountDetailsQuery;
import com.github.he305.contentcore.contentaccount.application.query.GetContentAccountIdQuery;
import com.github.he305.contentcore.contentaccount.application.services.GetContentAccountDetailsService;
import com.github.he305.contentcore.contentaccount.application.services.GetContentAccountIdService;
import com.github.he305.contentcore.contentaccount.application.services.GetContentAccountUrlService;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentAccountExchangeService {
    private final GetContentAccountIdService getContentAccountIdService;
    private final GetContentAccountDetailsService getContentAccountDetailsService;
    private final GetContentAccountUrlService getAccountUrlService;

    public UUID getContentAccountId(WatchingListContentAccount account) {
        Platform platform = Platform.valueOf(account.getPlatform().name());
        GetContentAccountIdQuery query = new GetContentAccountIdQuery(account.getName(), platform);
        return getContentAccountIdService.execute(query);
    }

    public ContentAccountDetails getContentAccountById(UUID id) {
        GetContentAccountDetailsQuery query = new GetContentAccountDetailsQuery(id);
        return getContentAccountDetailsService.execute(query);
    }

    public String getUrlForContentAccount(ContentAccountDetails details) {
        return getAccountUrlService.getUrL(details);
    }
}
