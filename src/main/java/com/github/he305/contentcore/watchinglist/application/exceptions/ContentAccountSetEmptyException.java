package com.github.he305.contentcore.watchinglist.application.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class ContentAccountSetEmptyException extends ContentCoreException {
    public ContentAccountSetEmptyException() {
        super("Content account set cannot be empty");
    }
}
