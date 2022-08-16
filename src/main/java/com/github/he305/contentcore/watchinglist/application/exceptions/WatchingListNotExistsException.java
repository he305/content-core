package com.github.he305.contentcore.watchinglist.application.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class WatchingListNotExistsException extends ContentCoreException {
    public WatchingListNotExistsException() {
        super("Watching list doesn't exist for this user");
    }
}
