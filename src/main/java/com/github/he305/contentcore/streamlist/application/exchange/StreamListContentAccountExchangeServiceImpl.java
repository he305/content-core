package com.github.he305.contentcore.streamlist.application.exchange;

import com.github.he305.contentcore.contentaccount.application.exchange.ContentAccountExchangeService;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StreamListContentAccountExchangeServiceImpl implements StreamListContentAccountExchangeService {
    private final ContentAccountExchangeService contentAccountExchangeService;

    @Override
    public ContentAccountDetails getContentAccount(UUID contentAccountId) {
        return contentAccountExchangeService.getContentAccountById(contentAccountId);
    }

    @Override
    public String getUrlContentAccount(ContentAccountDetails details) {
        return contentAccountExchangeService.getUrlForContentAccount(details);
    }
}
