package com.github.he305.contentcore.streamlist.domain.validators;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;

public interface StreamListPlatformValidator {
    boolean isStreamChannel(ContentAccountPlatform contentAccountPlatform);
}
