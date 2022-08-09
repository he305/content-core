package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.dto.query.GetNotificationForContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.query.NotificationDto;
import com.github.he305.contentcore.watchinglist.application.mapper.query.NotificationDtoMapper;
import com.github.he305.contentcore.watchinglist.application.query.GetNotificationForContentAccountQuery;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.*;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import com.github.he305.contentcore.watchinglist.domain.service.ContentAccountExchangeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GetNotificationForContentAccountServiceImplTest {

    @Mock
    private WatchingListRepository watchingListRepository;
    @Mock
    private ContentAccountExchangeService contentAccountExchangeService;
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
        assertThrows(IllegalArgumentException.class, () -> underTest.execute(query));
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
                Set.of(new ContentAccountId(contentAccountId)));
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), id.getId(), List.of(entry));
        watchingList.addNotificationForContentAccount(contentAccountId, notificationId);

        Mockito.when(watchingListRepository.getWatchingListByMemberId(id)).thenReturn(Optional.of(watchingList));
        ContentAccount account = new ContentAccount(query.getContentAccountName(), query.getPlatform());
        Mockito.when(contentAccountExchangeService.getContentAccountId(account)).thenReturn(new ContentAccountId(contentAccountId));
        List<NotificationDto> notificationDtoList = List.of(
                new NotificationDto("data")
        );
        Mockito.when(notificationDtoMapper.toDto(new NotificationId(notificationId))).thenReturn(new NotificationDto("data"));

        GetNotificationForContentAccountDto expected = new GetNotificationForContentAccountDto(
                notificationDtoList
        );

        GetNotificationForContentAccountDto actual = underTest.execute(query);

        assertEquals(expected, actual);
    }
}