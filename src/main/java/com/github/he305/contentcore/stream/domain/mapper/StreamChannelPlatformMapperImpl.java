package com.github.he305.contentcore.stream.domain.mapper;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.stream.domain.exceptions.ErrorCreatingStreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import org.springframework.stereotype.Component;

@Component
public class StreamChannelPlatformMapperImpl implements StreamChannelPlatformMapper {
    @Override
    public StreamChannelPlatform getStreamChannelPlatform(Platform contentAccountPlatform) throws ErrorCreatingStreamChannelPlatform {
        try {
            return StreamChannelPlatform.valueOf(contentAccountPlatform.name());
        } catch (IllegalArgumentException ex) {
            throw new ErrorCreatingStreamChannelPlatform(contentAccountPlatform);
        }
    }
}
