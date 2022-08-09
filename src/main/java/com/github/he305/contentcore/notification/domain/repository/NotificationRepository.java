package com.github.he305.contentcore.notification.domain.repository;

import com.github.he305.contentcore.notification.domain.model.Notification;

import java.util.UUID;

public interface NotificationRepository {
    void save(Notification notification);

    Notification findById(UUID id);
}
