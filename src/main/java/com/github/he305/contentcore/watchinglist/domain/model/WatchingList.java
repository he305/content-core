package com.github.he305.contentcore.watchinglist.domain.model;

import com.github.he305.contentcore.shared.util.SetUtils;
import com.github.he305.contentcore.watchinglist.domain.events.ContentAccountAddedEvent;
import com.github.he305.contentcore.watchinglist.domain.events.ContentAccountRemovedEvent;
import com.github.he305.contentcore.watchinglist.domain.events.WatchingListContentAccountAddedEvent;
import com.github.he305.contentcore.watchinglist.domain.events.WatchingListContentAccountRemovedEvent;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.*;

@Getter
@Slf4j
public class WatchingList extends AbstractAggregateRoot<WatchingList> {
    private final UUID id;
    private final MemberId memberId;
    private final List<WatchingListEntry> watchingListEntries = new ArrayList<>();

    public WatchingList(UUID id, UUID memberId) {
        this.id = id;
        this.memberId = new MemberId(memberId);
    }

    public WatchingList(UUID id, UUID memberId, List<WatchingListEntry> entries) {
        this.id = id;
        this.memberId = new MemberId(memberId);
        watchingListEntries.addAll(entries);
    }

    public void updateWatchingListEntry(String name, Set<ContentAccountId> contentAccountSet) {
        if (contentAccountSet.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Optional<WatchingListEntry> existingEntry = watchingListEntries.stream().filter(entry -> entry.getContentCreatorName().equals(name)).findAny();
        if (existingEntry.isEmpty()) {
            throw new IllegalArgumentException();
        }

        WatchingListEntry watchingListEntry = existingEntry.get();
        Set<ContentAccountId> existingSet = watchingListEntry.getContentAccountIdSet();
        Set<ContentAccountId> contentAccountIdsToAdd = SetUtils.findUniqueInFirstSet(contentAccountSet, existingSet);
        Set<ContentAccountId> contentAccountIdsToDelete = SetUtils.findUniqueInFirstSet(existingSet, contentAccountSet);

        contentAccountIdsToDelete.forEach(entry -> {
            if (watchingListEntry.removeContentAccount(entry)) {
                registerEvent(new ContentAccountRemovedEvent(entry.getId()));
                registerEvent(new WatchingListContentAccountRemovedEvent(entry.getId(), memberId.getId()));
            }
        });
        contentAccountIdsToAdd.forEach(entry -> {
            if (watchingListEntry.addContentAccount(entry)) {
                registerEvent(new ContentAccountAddedEvent(entry.getId()));
                registerEvent(new WatchingListContentAccountAddedEvent(entry.getId(), memberId.getId()));
            }
        });
    }

    public void addWatchingListEntry(String name, Set<ContentAccountId> contentAccountSet) {
        if (contentAccountSet.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Optional<WatchingListEntry> existingEntry = watchingListEntries.stream().filter(entry -> entry.getContentCreatorName().equals(name)).findAny();
        if (existingEntry.isPresent()) {
            throw new IllegalArgumentException();
        }

        WatchingListEntry watchingListEntry = new WatchingListEntry(new ContentCreator(name));

        contentAccountSet.forEach(entry -> {
            if (watchingListEntry.addContentAccount(entry)) {
                registerEvent(new ContentAccountAddedEvent(entry.getId()));
                registerEvent(new WatchingListContentAccountAddedEvent(entry.getId(), memberId.getId()));
            }
        });
        watchingListEntries.add(watchingListEntry);
    }

    public void deleteWatchingListEntry(String entryName) {
        WatchingListEntry existingEntry = watchingListEntries.stream()
                .filter(entry -> entry.getContentCreatorName().equals(entryName))
                .findAny().orElseThrow(IllegalArgumentException::new);

        Set<ContentAccountId> entryIds = existingEntry.getContentAccountIdSet();

        entryIds.forEach(entry -> {
            existingEntry.removeContentAccount(entry);
            registerEvent(new ContentAccountRemovedEvent(entry.getId()));
            registerEvent(new WatchingListContentAccountRemovedEvent(entry.getId(), memberId.getId()));
        });

        watchingListEntries.remove(existingEntry);
    }

    public void addNotificationForContentAccount(UUID contentAccountId, UUID notificationId) {
        watchingListEntries.forEach(
                watchingListEntry -> watchingListEntry.addNotificationForContentAccountId(new ContentAccountId(contentAccountId), notificationId)
        );
    }

    public Set<NotificationId> getNotificationsIdForContentAccountId(ContentAccountId contentAccountId) {
        Set<NotificationId> notificationIds = new HashSet<>();
        watchingListEntries.forEach(entry ->
                notificationIds.addAll(entry.getAndDeleteNotificationsForContentAccountId(contentAccountId))
        );
        return notificationIds;
    }

    public Collection<Object> getEvents() {
        return domainEvents();
    }
}
