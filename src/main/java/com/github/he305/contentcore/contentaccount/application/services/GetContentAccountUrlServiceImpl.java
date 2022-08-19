package com.github.he305.contentcore.contentaccount.application.services;

import com.github.he305.contentcore.contentaccount.application.services.urlservices.UrlContentAccountFactory;
import com.github.he305.contentcore.contentaccount.application.services.urlservices.UrlContentAccountService;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetContentAccountUrlServiceImpl implements GetContentAccountUrlService {
    private final UrlContentAccountFactory urlContentAccountFactory;

    @Override
    public String getUrL(ContentAccountDetails details) {
        UrlContentAccountService urlProvider = urlContentAccountFactory.getUrlContentAccountService(details.getPlatform());
        return urlProvider.getUrl(details.getName());
    }
}
