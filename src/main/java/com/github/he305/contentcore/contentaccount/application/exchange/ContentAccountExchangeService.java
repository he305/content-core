package com.github.he305.contentcore.contentaccount.application.exchange;

import com.github.he305.contentcore.contentaccount.application.query.GetContentAccountDetailsQuery;
import com.github.he305.contentcore.contentaccount.application.query.GetContentAccountIdQuery;
import com.github.he305.contentcore.contentaccount.application.services.GetContentAccountDetailsService;
import com.github.he305.contentcore.contentaccount.application.services.GetContentAccountIdService;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentAccountExchangeService {
    private final GetContentAccountIdService getContentAccountIdService;
    private final GetContentAccountDetailsService getContentAccountDetailsService;

    public ContentAccountId getContentAccountId(ContentAccount account) {
        Platform platform = Platform.valueOf(account.getPlatform().name());
        GetContentAccountIdQuery query = new GetContentAccountIdQuery(account.getName(), platform);
        UUID idToReturn = getContentAccountIdService.execute(query);
        return new ContentAccountId(idToReturn);
    }

    public ContentAccount getContentAccountById(UUID id) {
        GetContentAccountDetailsQuery query = new GetContentAccountDetailsQuery(id);
        ContentAccountDetails details = getContentAccountDetailsService.execute(query);
        ContentAccountPlatform platform = ContentAccountPlatform.valueOf(details.getPlatform().name());
        return new ContentAccount(details.getName(), platform);
    }
}
