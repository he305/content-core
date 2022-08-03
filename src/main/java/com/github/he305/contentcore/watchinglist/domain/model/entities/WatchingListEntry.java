package com.github.he305.contentcore.watchinglist.domain.model.entities;

import com.github.he305.contentcore.shared.entities.BaseEntity;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
public class WatchingListEntry extends BaseEntity {
    private final ContentCreator contentCreator;
    private final Set<ContentAccountId> contentAccountSet = new HashSet<>();

    public WatchingListEntry(ContentCreator contentCreator) {
        this.contentCreator = contentCreator;
    }

    public String getContentCreatorName() {
        return contentCreator.getName();
    }

    public boolean addContentAccount(ContentAccountId account) {
        return contentAccountSet.add(account);
    }

    public boolean removeContentAccount(ContentAccountId accountId) {
        return contentAccountSet.remove(accountId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WatchingListEntry that = (WatchingListEntry) o;
        return contentCreator.equals(that.contentCreator) && contentAccountSet.equals(that.contentAccountSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contentCreator, contentAccountSet);
    }
}
