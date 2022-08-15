package com.github.he305.contentcore.streamlist.domain.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class StreamListNotFoundException extends ContentCoreException {
    public StreamListNotFoundException(String message) {
        super(message);
    }
}
