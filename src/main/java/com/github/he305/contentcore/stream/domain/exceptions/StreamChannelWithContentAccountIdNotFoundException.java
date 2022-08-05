package com.github.he305.contentcore.stream.domain.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;

public class StreamChannelWithContentAccountIdNotFoundException extends ContentCoreException {
    public StreamChannelWithContentAccountIdNotFoundException(StreamChannelContentAccountId id) {
        super("StreamChannel with contentAccountId " + id.toString() + " not found");
    }
}
