package com.github.he305.contentcore.notification.domain.model;

import com.github.he305.contentcore.notification.domain.events.NewNotificationEvent;
import com.github.he305.contentcore.notification.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.notification.domain.model.values.NotificationData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Notification extends AbstractAggregateRoot<Notification> {
    private final UUID id;
    private final ContentAccountId contentAccountId;
    private final NotificationData data;

    public Notification(String message, UUID contentAccountId, LocalDateTime time) {
        this.id = UUID.randomUUID();
        this.contentAccountId = new ContentAccountId(contentAccountId);
        this.data = new NotificationData(time, message);
        registerEvent(new NewNotificationEvent(id, contentAccountId));
    }

    public Notification(UUID id, ContentAccountId contentAccountId, NotificationData data) {
        this.id = id;
        this.contentAccountId = contentAccountId;
        this.data = data;
    }

    public Collection<Object> getEvents() {
        return domainEvents();
    }
}
