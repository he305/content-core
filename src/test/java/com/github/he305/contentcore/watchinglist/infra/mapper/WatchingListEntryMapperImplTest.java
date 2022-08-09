package com.github.he305.contentcore.watchinglist.infra.mapper;

import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListData;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListEntryData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WatchingListEntryMapperImplTest {

    @Mock
    private ContentAccountEntryMapper contentAccountEntryMapper;

    @InjectMocks
    private WatchingListEntryMapperImpl underTest;


    @Test
    void toJpa() {
        WatchingListEntry entry = new WatchingListEntry(UUID.randomUUID(), new ContentCreator("name"), Set.of());
        WatchingListData watchingListData = new WatchingListData(UUID.randomUUID(), UUID.randomUUID(), Collections.emptyList());
        WatchingListEntryData expected = new WatchingListEntryData(entry.getId(), "name", Collections.emptyList(), watchingListData);

        WatchingListEntryData actual = underTest.toJpa(entry, watchingListData);
        assertEquals(expected, actual);
    }

    @Test
    void toDomain() {
        WatchingListEntryData entryData = new WatchingListEntryData(
                UUID.randomUUID(),
                "name",
                Collections.emptyList(),
                WatchingListData.builder().build()
        );
        WatchingListEntry expected = new WatchingListEntry(
                entryData.getId(),
                new ContentCreator("name"),
                Set.of()
        );

        WatchingListEntry actual = underTest.toDomain(entryData);
        assertEquals(expected, actual);
    }
}