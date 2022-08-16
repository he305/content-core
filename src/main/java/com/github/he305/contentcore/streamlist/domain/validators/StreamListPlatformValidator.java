package com.github.he305.contentcore.streamlist.domain.validators;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;

public interface StreamListPlatformValidator {
    boolean isStreamChannel(Platform contentAccountPlatform);
}
