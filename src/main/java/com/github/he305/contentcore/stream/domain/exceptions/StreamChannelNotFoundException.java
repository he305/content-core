package com.github.he305.contentcore.stream.domain.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class StreamChannelNotFoundException extends ContentCoreException {
    public StreamChannelNotFoundException() {
        super("Stream channel doesn't exist");
    }
}
