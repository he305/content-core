package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.dto.query.GetNotificationForContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.query.NotificationDto;
import com.github.he305.contentcore.watchinglist.application.exceptions.WatchingListNotExistsException;
import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccount;
import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccountExchangeService;
import com.github.he305.contentcore.watchinglist.application.mapper.query.NotificationDtoMapper;
import com.github.he305.contentcore.watchinglist.application.query.GetNotificationForContentAccountQuery;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.*;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GetNotificationForWatchingListContentAccountServiceImplTest {

    @Mock
    private WatchingListRepository watchingListRepository;
    @Mock
    private WatchingListContentAccountExchangeService watchingListContentAccountExchangeService;
    @Mock
    private NotificationDtoMapper notificationDtoMapper;

    @InjectMocks
    private GetNotificationForContentAccountServiceImpl underTest;

    @Test
    void execute_empty() {
        MemberId id = new MemberId(UUID.randomUUID());
        GetNotificationForContentAccountQuery query = new GetNotificationForContentAccountQuery(
                id,
                "name",
                ContentAccountPlatform.TWITCH
        );
        Mockito.when(watchingListRepository.getWatchingListByMemberId(id)).thenReturn(Optional.empty());
        assertThrows(WatchingListNotExistsException.class, () -> underTest.execute(query));
    }

    @Test
    void execute_valid() {
        MemberId id = new MemberId(UUID.randomUUID());
        GetNotificationForContentAccountQuery query = new GetNotificationForContentAccountQuery(
                id,
                "name",
                ContentAccountPlatform.TWITCH
        );

        UUID contentAccountId = UUID.randomUUID();
        UUID notificationId = UUID.randomUUID();

        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"),
                Set.of(new ContentAccountEntry("test", new WatchingListContentAccountId(contentAccountId))));
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), id.getId(), List.of(entry));
        watchingList.addNotificationForContentAccount(contentAccountId, notificationId);

        Mockito.when(watchingListRepository.getWatchingListByMemberId(id)).thenReturn(Optional.of(watchingList));
        WatchingListContentAccount account = new WatchingListContentAccount(query.getContentAccountName(), query.getPlatform());
        Mockito.when(watchingListContentAccountExchangeService.getContentAccountId(account)).thenReturn(contentAccountId);
        LocalDateTime time = LocalDateTime.now();
        List<NotificationDto> notificationDtoList = List.of(
                new NotificationDto(time, "data")
        );
        Mockito.when(notificationDtoMapper.toDto(new NotificationId(notificationId))).thenReturn(new NotificationDto(time, "data"));

        GetNotificationForContentAccountDto expected = new GetNotificationForContentAccountDto(
                notificationDtoList
        );

        GetNotificationForContentAccountDto actual = underTest.execute(query);

        assertEquals(expected, actual);
    }
}