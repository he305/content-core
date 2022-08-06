package com.github.he305.contentcore.contentaccount.domain.model.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class UseCounterTooHighException extends ContentCoreException {

    public UseCounterTooHighException() {
        super("Use counter can't be higher than MAX INT");
    }
}
