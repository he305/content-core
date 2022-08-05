package com.github.he305.contentcore.stream.domain.exceptions;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class ErrorCreatingStreamChannelPlatform extends ContentCoreException {
    public ErrorCreatingStreamChannelPlatform(Platform platform) {
        super(platform.name() + " can't be mapped to any in StreamChannelPlatform");
    }
}
