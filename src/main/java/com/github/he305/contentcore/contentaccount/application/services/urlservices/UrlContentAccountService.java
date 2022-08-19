package com.github.he305.contentcore.contentaccount.application.services.urlservices;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;

public interface UrlContentAccountService {
    String getUrl(String accountName);

    Platform getType();
}
