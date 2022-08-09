package com.github.he305.contentcore.notification.domain.service;

import com.github.he305.contentcore.notification.domain.model.Notification;

import java.util.UUID;

public interface NotificationService {
    Notification findById(UUID id);
}
