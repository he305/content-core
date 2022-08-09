package com.github.he305.contentcore.notification.domain.exceptions;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class NotificationNotFoundException extends ContentCoreException {
    public NotificationNotFoundException(String message) {
        super(message);
    }
}
