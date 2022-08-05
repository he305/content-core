package com.github.he305.contentcore.stream.domain.mapper;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.stream.domain.exceptions.ErrorCreatingStreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;

public interface StreamChannelPlatformMapper {
    StreamChannelPlatform getStreamChannelPlatform(Platform contentAccountPlatform) throws ErrorCreatingStreamChannelPlatform;
}
