package com.github.he305.contentcore.contentaccount.application.services.urlservices;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import org.springframework.stereotype.Service;

@Service
public class WasdUrlContentAccountService implements UrlContentAccountService {
    @Override
    public String getUrl(String accountName) {
        return String.format("https://wasd.tv/%s", accountName);
    }

    @Override
    public Platform getType() {
        return Platform.WASD;
    }
}
