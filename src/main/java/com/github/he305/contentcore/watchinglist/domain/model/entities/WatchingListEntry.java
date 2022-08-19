package com.github.he305.contentcore.watchinglist.domain.model.entities;

import com.github.he305.contentcore.shared.util.SetUtils;
import com.github.he305.contentcore.watchinglist.domain.model.enums.WatchingListEntryUpdateResult;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import com.github.he305.contentcore.watchinglist.domain.model.values.WatchingListContentAccountId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.*;
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

    public WatchingListEntry(ContentCreator contentCreator, Set<ContentAccountEntry> set) {
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

    public boolean addContentAccount(ContentAccountEntry newEntry) {
        Optional<ContentAccountEntry> entry = contentAccountSet
                .stream()
                .filter(contentAccountEntry -> contentAccountEntry.getWatchingListContentAccountId().equals(newEntry.getWatchingListContentAccountId()))
                .findAny();
        if (entry.isPresent()) {
            return false;
        }
        return contentAccountSet.add(newEntry);
    }

    public boolean removeContentAccount(ContentAccountEntry newEntry) {
        Optional<ContentAccountEntry> entry = contentAccountSet
                .stream()
                .filter(contentAccountEntry -> contentAccountEntry.getWatchingListContentAccountId().equals(newEntry.getWatchingListContentAccountId()))
                .findAny();
        if (entry.isEmpty()) {
            return false;
        }

        return contentAccountSet.remove(entry.get());
    }

    public Map<ContentAccountEntry, WatchingListEntryUpdateResult> updateEntry(Set<ContentAccountEntry> newEntries) {
        Map<ContentAccountEntry, WatchingListEntryUpdateResult> resultMap = new HashMap<>();
        newEntries.forEach(newEntry -> {
            Optional<ContentAccountEntry> entry = contentAccountSet
                    .stream()
                    .filter(contentAccountEntry -> contentAccountEntry.getWatchingListContentAccountId().equals(newEntry.getWatchingListContentAccountId()))
                    .findAny();
            if (entry.isPresent()) {
                if (!newEntry.getAlias().equals(entry.get().getAlias())) {
                    entry.get().setAlias(newEntry.getAlias());
                    resultMap.put(newEntry, WatchingListEntryUpdateResult.NAME_CHANGED);
                    return;
                }
                resultMap.put(newEntry, WatchingListEntryUpdateResult.UNCHANGED);
                return;
            }

            contentAccountSet.add(newEntry);
            resultMap.put(newEntry, WatchingListEntryUpdateResult.ADDED);
        });

        Set<ContentAccountEntry> contentAccountEntriesToDelete = SetUtils.findUniqueInFirstSet(contentAccountSet, newEntries);

        contentAccountEntriesToDelete.forEach(contentAccountEntryToDelete -> {
            contentAccountSet.remove(contentAccountEntryToDelete);
            resultMap.put(contentAccountEntryToDelete, WatchingListEntryUpdateResult.REMOVED);
        });

        return resultMap;
    }

    public void addNotificationForContentAccountId(WatchingListContentAccountId watchingListContentAccountId, UUID notificationId) {
        Optional<ContentAccountEntry> entry = contentAccountSet
                .stream()
                .filter(contentAccountEntry -> contentAccountEntry.getWatchingListContentAccountId().equals(watchingListContentAccountId))
                .findAny();
        if (entry.isEmpty()) {
            return;
        }
        entry.get().addNotificationId(new NotificationId(notificationId));
    }

    public Set<NotificationId> getAndDeleteNotificationsForContentAccountId(WatchingListContentAccountId watchingListContentAccountId) {
        Optional<ContentAccountEntry> entry = contentAccountSet
                .stream()
                .filter(contentAccountEntry -> contentAccountEntry.getWatchingListContentAccountId().equals(watchingListContentAccountId))
                .findAny();
        if (entry.isEmpty()) {
            return Set.of();
        }

        Set<NotificationId> set = new HashSet<>(entry.get().getNotificationIds());
        entry.get().clearNotificationIds();
        return set;
    }

    public Set<WatchingListContentAccountId> getContentAccountIdSet() {
        return contentAccountSet.stream()
                .map(contentAccountEntry -> new WatchingListContentAccountId(contentAccountEntry.getWatchingListContentAccountId().getId()))
                .collect(Collectors.toSet());
    }
}
