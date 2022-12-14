package com.github.he305.contentcore.streamlist.domain.sevice;

import com.github.he305.contentcore.contentaccount.application.exchange.ContentAccountExchangeService;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.domain.repository.StreamListRepository;
import com.github.he305.contentcore.streamlist.domain.validators.StreamListPlatformValidator;
import com.github.he305.contentcore.watchinglist.domain.events.WatchingListContentAccountAddedEvent;
import com.github.he305.contentcore.watchinglist.domain.events.WatchingListContentAccountRemovedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class StreamListServiceImplTest {

    @Mock
    private StreamListRepository streamListRepository;
    @Mock
    private ContentAccountExchangeService contentAccountExchangeService;
    @Mock
    private StreamListPlatformValidator streamListPlatformValidator;

    @InjectMocks
    private StreamListServiceImpl underTest;

    @Test
    void handleWatchingListContentAccountAddedEvent_notStreamChannel() {
        UUID contentAccountId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        WatchingListContentAccountAddedEvent event = new WatchingListContentAccountAddedEvent(contentAccountId, memberId);

        ContentAccountDetails contentAccountDetails = new ContentAccountDetails("name", null);
        Mockito.when(contentAccountExchangeService.getContentAccountById(contentAccountId)).thenReturn(contentAccountDetails);
        Mockito.when(streamListPlatformValidator.isStreamChannel(contentAccountDetails.getPlatform())).thenReturn(false);

        assertDoesNotThrow(() -> underTest.handleWatchingListContentAccountAddedEvent(event));
    }

    @Test
    void handleWatchingListContentAccountAddedEvent_createNew() {
        UUID contentAccountId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        WatchingListContentAccountAddedEvent event = new WatchingListContentAccountAddedEvent(contentAccountId, memberId);

        ContentAccountDetails contentAccountDetails = new ContentAccountDetails("name", Platform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccountById(contentAccountId)).thenReturn(contentAccountDetails);
        Mockito.when(streamListPlatformValidator.isStreamChannel(contentAccountDetails.getPlatform())).thenReturn(true);
        Mockito.when(streamListRepository.getByMemberId(new MemberId(memberId))).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> underTest.handleWatchingListContentAccountAddedEvent(event));
    }

    @Test
    void handleWatchingListContentAccountAddedEvent_valid() {
        UUID contentAccountId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        StreamList streamList = new StreamList(memberId);

        ContentAccountDetails contentAccountDetails = new ContentAccountDetails("name", Platform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccountById(contentAccountId)).thenReturn(contentAccountDetails);
        Mockito.when(streamListPlatformValidator.isStreamChannel(contentAccountDetails.getPlatform())).thenReturn(true);
        WatchingListContentAccountAddedEvent event = new WatchingListContentAccountAddedEvent(contentAccountId, memberId);
        Mockito.when(streamListRepository.getByMemberId(new MemberId(memberId))).thenReturn(Optional.of(streamList));

        assertDoesNotThrow(() -> underTest.handleWatchingListContentAccountAddedEvent(event));
    }

    @Test
    void handleWatchingListContentAccountRemovedEvent_notStreamChannel() {
        UUID contentAccountId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        WatchingListContentAccountRemovedEvent event = new WatchingListContentAccountRemovedEvent(contentAccountId, memberId);

        ContentAccountDetails contentAccountDetails = new ContentAccountDetails("name", null);
        Mockito.when(contentAccountExchangeService.getContentAccountById(contentAccountId)).thenReturn(contentAccountDetails);
        Mockito.when(streamListPlatformValidator.isStreamChannel(contentAccountDetails.getPlatform())).thenReturn(false);

        assertDoesNotThrow(() -> underTest.handleWatchingListContentAccountRemovedEvent(event));
    }

    @Test
    void handleWatchingListContentAccountRemovedEvent_createNew() {
        UUID contentAccountId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();

        ContentAccountDetails contentAccountDetails = new ContentAccountDetails("name", Platform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccountById(contentAccountId)).thenReturn(contentAccountDetails);
        Mockito.when(streamListPlatformValidator.isStreamChannel(contentAccountDetails.getPlatform())).thenReturn(true);
        WatchingListContentAccountRemovedEvent event = new WatchingListContentAccountRemovedEvent(contentAccountId, memberId);
        Mockito.when(streamListRepository.getByMemberId(new MemberId(memberId))).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> underTest.handleWatchingListContentAccountRemovedEvent(event));
    }

    @Test
    void handleWatchingListContentAccountRemovedEvent_valid() {
        UUID contentAccountId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        StreamList streamList = new StreamList(memberId);

        ContentAccountDetails contentAccountDetails = new ContentAccountDetails("name", Platform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccountById(contentAccountId)).thenReturn(contentAccountDetails);
        Mockito.when(streamListPlatformValidator.isStreamChannel(contentAccountDetails.getPlatform())).thenReturn(true);
        WatchingListContentAccountRemovedEvent event = new WatchingListContentAccountRemovedEvent(contentAccountId, memberId);
        Mockito.when(streamListRepository.getByMemberId(new MemberId(memberId))).thenReturn(Optional.of(streamList));

        assertDoesNotThrow(() -> underTest.handleWatchingListContentAccountRemovedEvent(event));
    }
}