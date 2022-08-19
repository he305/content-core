package com.github.he305.contentcore.watchinglist.application.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class UpdateWatchingListEntryEqualNamesException extends ContentCoreException {

    public UpdateWatchingListEntryEqualNamesException(String oldName, String newName) {
        super(oldName + " and " + newName + " are equal, operation denied");
    }
}
