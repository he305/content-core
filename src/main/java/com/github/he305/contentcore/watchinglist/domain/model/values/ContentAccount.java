package com.github.he305.contentcore.watchinglist.domain.model.values;

import com.github.he305.contentcore.shared.validators.StringValidator;
import lombok.Value;

@Value
public class ContentAccount {
    String name;
    ContentAccountPlatform platform;

    public ContentAccount(String name, ContentAccountPlatform platform) {
        this.name = StringValidator.isNullOrEmpty(name);
        this.platform = platform;
    }
}
