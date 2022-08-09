package com.github.he305.contentcore.watchinglist.domain.model.entities;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class WatchingListEntry {
    private final UUID id;
    private final ContentCreator contentCreator;
    private Set<ContentAccountEntry> contentAccountSet = new HashSet<>();

    public WatchingListEntry(ContentCreator contentCreator) {
        this.id = UUID.randomUUID();
        this.contentCreator = contentCreator;
    }

    public WatchingListEntry(ContentCreator contentCreator, Set<ContentAccountId> set) {
        this.id = UUID.randomUUID();
        this.contentCreator = contentCreator;
        set.forEach(this::addContentAccount);
    }

    public WatchingListEntry(UUID id, ContentCreator contentCreator, Set<ContentAccountEntry> set) {
        this.id = id;
        this.contentCreator = contentCreator;
        this.contentAccountSet = set;
    }

    public String getContentCreatorName() {
        return contentCreator.getName();
    }

    public boolean addContentAccount(ContentAccountId account) {
        Optional<ContentAccountEntry> entry = contentAccountSet
                .stream()
                .filter(contentAccountEntry -> contentAccountEntry.getContentAccountId().equals(account))
                .findAny();
        if (entry.isPresent()) {
            return false;
        }
        return contentAccountSet.add(new ContentAccountEntry(account));
    }

    public boolean removeContentAccount(ContentAccountId accountId) {
        Optional<ContentAccountEntry> entry = contentAccountSet
                .stream()
                .filter(contentAccountEntry -> contentAccountEntry.getContentAccountId().equals(accountId))
                .findAny();
        if (entry.isEmpty()) {
            return false;
        }

        return contentAccountSet.remove(entry.get());
    }

    public void addNotificationForContentAccountId(ContentAccountId contentAccountId, UUID notificationId) {
        Optional<ContentAccountEntry> entry = contentAccountSet
                .stream()
                .filter(contentAccountEntry -> contentAccountEntry.getContentAccountId().equals(contentAccountId))
                .findAny();
        if (entry.isEmpty()) {
            return;
        }
        entry.get().addNotificationId(new NotificationId(notificationId));
    }

    public Set<NotificationId> getAndDeleteNotificationsForContentAccountId(ContentAccountId contentAccountId) {
        Optional<ContentAccountEntry> entry = contentAccountSet
                .stream()
                .filter(contentAccountEntry -> contentAccountEntry.getContentAccountId().equals(contentAccountId))
                .findAny();
        if (entry.isEmpty()) {
            return Set.of();
        }

        Set<NotificationId> set = new HashSet<>(entry.get().getNotificationIds());
        entry.get().clearNotificationIds();
        return set;
    }

    public Set<ContentAccountId> getContentAccountIdSet() {
        return contentAccountSet.stream()
                .map(contentAccountEntry -> new ContentAccountId(contentAccountEntry.getContentAccountId().getId()))
                .collect(Collectors.toSet());
    }
}
