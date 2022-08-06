package com.github.he305.contentcore.stream.domain.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class StreamChannelIsAlreadyObservableException extends ContentCoreException {
    public StreamChannelIsAlreadyObservableException() {
        super("StreamChannel is already observable");
    }
}
