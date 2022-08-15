package com.github.he305.contentcore.watchinglist.infra.mapper;

import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import com.github.he305.contentcore.watchinglist.infra.data.ContentAccountEntryData;
import com.github.he305.contentcore.watchinglist.infra.data.NotificationIdData;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListEntryData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContentAccountEntryMapperImplTest {

    private ContentAccountEntryMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new ContentAccountEntryMapperImpl();
    }

    @Test
    void toJpa() {
        WatchingListEntryData data = WatchingListEntryData.builder()
                .id(UUID.randomUUID())
                .build();

        UUID id = UUID.randomUUID();
        UUID contentAccountId = UUID.randomUUID();
        UUID notificationId = UUID.randomUUID();
        ContentAccountEntry entry = new ContentAccountEntry(
                id,
                "test",
                new ContentAccountId(contentAccountId),
                Set.of(new NotificationId(notificationId))
        );
        ContentAccountEntryData expected = new ContentAccountEntryData(
                id,
                "test",
                contentAccountId,
                Set.of(new NotificationIdData(notificationId)),
                data
        );

        ContentAccountEntryData actual = underTest.toJpa(entry, data);
        assertEquals(expected, actual);
    }

    @Test
    void toDomain() {
        WatchingListEntryData data = WatchingListEntryData.builder()
                .id(UUID.randomUUID())
                .build();

        UUID id = UUID.randomUUID();
        UUID contentAccountId = UUID.randomUUID();
        UUID notificationId = UUID.randomUUID();
        ContentAccountEntry expected = new ContentAccountEntry(
                id,
                "test",
                new ContentAccountId(contentAccountId),
                Set.of(new NotificationId(notificationId))
        );
        ContentAccountEntryData jpa = new ContentAccountEntryData(
                id,
                "test",
                contentAccountId,
                Set.of(new NotificationIdData(notificationId)),
                data
        );

        ContentAccountEntry actual = underTest.toDomain(jpa);
        assertEquals(expected, actual);
    }
}