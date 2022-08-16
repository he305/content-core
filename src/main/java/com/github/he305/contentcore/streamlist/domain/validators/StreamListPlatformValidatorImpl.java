package com.github.he305.contentcore.streamlist.domain.validators;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.streamlist.domain.model.values.StreamListPlatform;
import org.springframework.stereotype.Component;

@Component
public class StreamListPlatformValidatorImpl implements StreamListPlatformValidator {
    @Override
    public boolean isStreamChannel(Platform contentAccountPlatform) {
        try {
            StreamListPlatform.valueOf(contentAccountPlatform.name());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
