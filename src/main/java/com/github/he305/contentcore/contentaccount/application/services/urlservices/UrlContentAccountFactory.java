package com.github.he305.contentcore.contentaccount.application.services.urlservices;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;

public interface UrlContentAccountFactory {
    UrlContentAccountService getUrlContentAccountService(Platform platform);
}
