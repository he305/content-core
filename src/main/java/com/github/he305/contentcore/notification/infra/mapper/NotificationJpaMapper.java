package com.github.he305.contentcore.notification.infra.mapper;

import com.github.he305.contentcore.notification.domain.model.Notification;
import com.github.he305.contentcore.notification.infra.data.NotificationJpa;

public interface NotificationJpaMapper {
    NotificationJpa toJpa(Notification notification);

    Notification toDomain(NotificationJpa jpa);
}
