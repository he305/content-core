package com.github.he305.contentcore.watchinglist.application.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class WatchingListEntryAlreadyExistException extends ContentCoreException {
    public WatchingListEntryAlreadyExistException(String entryName) {
        super("Entry with name " + entryName + " already exists");
    }
}
