package com.github.he305.contentcore.stream.domain.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class StreamChannelIsAlreadyFrozenException extends ContentCoreException {
    public StreamChannelIsAlreadyFrozenException() {
        super("StreamChannel is already frozen");
    }
}
