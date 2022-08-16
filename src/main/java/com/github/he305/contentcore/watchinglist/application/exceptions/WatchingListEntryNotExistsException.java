package com.github.he305.contentcore.watchinglist.application.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class WatchingListEntryNotExistsException extends ContentCoreException {
    public WatchingListEntryNotExistsException(String entryName) {
        super("Entry with name " + entryName + " doesn't exist");
    }
}
