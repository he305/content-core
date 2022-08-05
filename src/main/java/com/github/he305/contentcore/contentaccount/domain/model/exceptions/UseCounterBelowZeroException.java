package com.github.he305.contentcore.contentaccount.domain.model.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class UseCounterBelowZeroException extends ContentCoreException {
    public UseCounterBelowZeroException() {
        super("Use counter can't be lower than 0");
    }
}
