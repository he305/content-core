package com.github.he305.contentcore.watchinglist.domain.model;

import com.github.he305.contentcore.shared.util.SetUtils;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.*;

@Getter
public class WatchingList extends AbstractAggregateRoot<WatchingList> {
    private final UUID id;
    private final MemberId memberId;
    private final List<WatchingListEntry> watchingListEntries = new ArrayList<>();

    public WatchingList(UUID id, UUID memberId) {
        this.id = id;
        this.memberId = new MemberId(memberId);
    }

    public void updateWatchingListEntry(String name, Set<ContentAccountId> contentAccountSet) {
        Optional<WatchingListEntry> existingEntry = watchingListEntries.stream().filter(entry -> entry.getContentCreatorName().equals(name)).findAny();
        if (existingEntry.isEmpty()) {
            throw new IllegalArgumentException();
        }

        WatchingListEntry watchingListEntry = existingEntry.get();
        Set<ContentAccountId> existingSet = watchingListEntry.getContentAccountSet();
        Set<ContentAccountId> contentAccountIdsToAdd = SetUtils.findUniqueInFirstSet(contentAccountSet, existingSet);
        Set<ContentAccountId> contentAccountIdsToDelete = SetUtils.findUniqueInFirstSet(existingSet, contentAccountSet);

        contentAccountIdsToDelete.forEach(watchingListEntry::removeContentAccount);
        contentAccountIdsToAdd.forEach(watchingListEntry::addContentAccount);
    }

    public void addWatchingListEntry(String name, Set<ContentAccountId> contentAccountSet) {
        Optional<WatchingListEntry> existingEntry = watchingListEntries.stream().filter(entry -> entry.getContentCreatorName().equals(name)).findAny();
        if (existingEntry.isPresent()) {
            addContentAccountSetForExistingEntry(existingEntry.get(), contentAccountSet);
            return;
        }

        WatchingListEntry watchingListEntry = new WatchingListEntry(new ContentCreator(name));


        // TODO: event
        contentAccountSet.forEach(watchingListEntry::addContentAccount);
        watchingListEntries.add(watchingListEntry);
    }

    private void addContentAccountSetForExistingEntry(WatchingListEntry watchingListEntry, Set<ContentAccountId> contentAccountSet) {
        contentAccountSet.forEach(watchingListEntry::addContentAccount);
    }
}
