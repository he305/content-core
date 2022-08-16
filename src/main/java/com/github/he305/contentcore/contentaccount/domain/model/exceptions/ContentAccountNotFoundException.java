package com.github.he305.contentcore.contentaccount.domain.model.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class ContentAccountNotFoundException extends ContentCoreException {
    public ContentAccountNotFoundException() {
        super("Content account doesn't exist");
    }
}
