package com.github.he305.contentcore.watchinglist.domain.model.entities;

import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import com.github.he305.contentcore.watchinglist.domain.model.values.WatchingListContentAccountId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class ContentAccountEntry {
    private final UUID id;
    private final String alias;
    private final WatchingListContentAccountId watchingListContentAccountId;
    private final Set<NotificationId> notificationIds;

    public ContentAccountEntry(String alias, WatchingListContentAccountId watchingListContentAccountId) {
        this.id = UUID.randomUUID();
        this.alias = alias;
        this.watchingListContentAccountId = watchingListContentAccountId;
        this.notificationIds = new HashSet<>();
    }

    public ContentAccountEntry(UUID id, String alias, WatchingListContentAccountId watchingListContentAccountId, Set<NotificationId> notificationIds) {
        this.id = id;
        this.alias = alias;
        this.watchingListContentAccountId = watchingListContentAccountId;
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
