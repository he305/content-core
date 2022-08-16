package com.github.he305.contentcore.watchinglist.application.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class WatchingListAlreadyExistsException extends ContentCoreException {
    public WatchingListAlreadyExistsException() {
        super("Watching list for user already exists. To modify list use another endpoints");
    }
}
