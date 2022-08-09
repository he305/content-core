package com.github.he305.contentcore.watchinglist.infra.mapper;

import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import com.github.he305.contentcore.watchinglist.infra.data.ContentAccountEntryData;
import com.github.he305.contentcore.watchinglist.infra.data.NotificationIdData;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListEntryData;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ContentAccountEntryMapperImpl implements ContentAccountEntryMapper {
    @Override
    public ContentAccountEntryData toJpa(ContentAccountEntry entry, WatchingListEntryData watchingListEntryData) {
        Set<NotificationIdData> notificationIdData = entry.getNotificationIds()
                .stream()
                .map(notificationId -> new NotificationIdData(notificationId.getId()))
                .collect(Collectors.toSet());

        return new ContentAccountEntryData(
                entry.getId(),
                entry.getContentAccountId().getId(),
                notificationIdData,
                watchingListEntryData
        );
    }

    @Override
    public ContentAccountEntry toDomain(ContentAccountEntryData jpa) {
        Set<NotificationId> notificationIds = jpa.getNotificationIds()
                .stream()
                .map(notificationIdData -> new NotificationId(notificationIdData.getId()))
                .collect(Collectors.toSet());

        return new ContentAccountEntry(
                jpa.getId(),
                new ContentAccountId(jpa.getContentAccountId()),
                notificationIds
        );
    }
}