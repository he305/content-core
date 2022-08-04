package com.github.he305.contentcore.watchinglist.infra.mapper;

import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.infra.data.ContentAccountIdData;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListData;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListEntryData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WatchingListEntryMapperImplTest {

    private WatchingListEntryMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new WatchingListEntryMapperImpl();
    }

    @Test
    void toJpa() {
        UUID contentAccountId = UUID.randomUUID();
        WatchingListEntry entry = new WatchingListEntry(UUID.randomUUID(), new ContentCreator("name"), Set.of(new ContentAccountId(contentAccountId)));
        WatchingListData watchingListData = new WatchingListData(UUID.randomUUID(), UUID.randomUUID(), Collections.emptyList());
        WatchingListEntryData expected = new WatchingListEntryData(entry.getId(), "name", Set.of(new ContentAccountIdData(contentAccountId)), watchingListData);

        WatchingListEntryData actual = underTest.toJpa(entry, watchingListData);
        assertEquals(expected, actual);
    }

    @Test
    void toDomain() {
        UUID contentAccountId = UUID.randomUUID();
        WatchingListEntryData entryData = new WatchingListEntryData(
                UUID.randomUUID(),
                "name",
                Set.of(new ContentAccountIdData(contentAccountId)),
                WatchingListData.builder().build()
        );
        WatchingListEntry expected = new WatchingListEntry(
                entryData.getId(),
                new ContentCreator("name"),
                Set.of(new ContentAccountId(contentAccountId))
        );

        WatchingListEntry actual = underTest.toDomain(entryData);
        assertEquals(expected, actual);
    }
}