package com.github.he305.contentcore.watchinglist.domain.model.entities;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class ContentAccountEntry {
    private final UUID id;
    private final ContentAccountId contentAccountId;
    private final Set<NotificationId> notificationIds;

    public ContentAccountEntry(ContentAccountId contentAccountId) {
        this.id = UUID.randomUUID();
        this.contentAccountId = contentAccountId;
        this.notificationIds = new HashSet<>();
    }

    public ContentAccountEntry(UUID id, ContentAccountId contentAccountId, Set<NotificationId> notificationIds) {
        this.id = id;
        this.contentAccountId = contentAccountId;
        this.notificationIds = notificationIds;
    }

    public boolean addNotificationId(NotificationId id) {
        return notificationIds.add(id);
    }

    public void clearNotificationIds() {
        this.notificationIds.clear();
    }

    public int getNotificationSize() {
        return notificationIds.size();
    }
}
