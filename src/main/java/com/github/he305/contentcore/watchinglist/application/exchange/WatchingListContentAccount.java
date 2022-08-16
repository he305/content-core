package com.github.he305.contentcore.watchinglist.application.exchange;

import com.github.he305.contentcore.shared.validators.StringValidator;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class WatchingListContentAccount {
    private final String name;
    private final ContentAccountPlatform platform;

    public WatchingListContentAccount(String name, ContentAccountPlatform platform) {
        this.name = StringValidator.isNullOrEmpty(name);
        this.platform = platform;
    }
}
