package com.github.he305.contentcore.notification.infra.mapper;

import com.github.he305.contentcore.notification.domain.model.Notification;
import com.github.he305.contentcore.notification.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.notification.domain.model.values.NotificationData;
import com.github.he305.contentcore.notification.infra.data.NotificationJpa;
import org.springframework.stereotype.Component;

@Component
public class NotificationJpaMapperImpl implements NotificationJpaMapper {
    @Override
    public NotificationJpa toJpa(Notification notification) {
        return new NotificationJpa(
                notification.getId(),
                notification.getContentAccountId().getId(),
                notification.getData().getMessage(),
                notification.getData().getTime()
        );
    }

    @Override
    public Notification toDomain(NotificationJpa jpa) {
        return new Notification(
                jpa.getId(),
                new ContentAccountId(jpa.getContentAccountId()),
                new NotificationData(jpa.getMessageTime(), jpa.getMessage())
        );
    }
}
