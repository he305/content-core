package com.github.he305.contentcore.stream.domain.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class NoLiveStreamFoundException extends ContentCoreException {
    public NoLiveStreamFoundException() {
        super("No live streams found");
    }
}
