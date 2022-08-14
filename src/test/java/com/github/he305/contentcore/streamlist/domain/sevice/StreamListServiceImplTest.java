package com.github.he305.contentcore.streamlist.domain.sevice;

import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.domain.repository.StreamListRepository;
import com.github.he305.contentcore.watchinglist.domain.events.ContentAccountAddedEvent;
import com.github.he305.contentcore.watchinglist.domain.events.ContentAccountRemovedEvent;
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

    @InjectMocks
    private StreamListServiceImpl underTest;

    @Test
    void handleContentAccountAddedEvent_createNew() {
        UUID contentAccountId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        ContentAccountAddedEvent event = new ContentAccountAddedEvent(contentAccountId, memberId);
        Mockito.when(streamListRepository.getByMemberId(new MemberId(memberId))).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> underTest.handleContentAccountAddedEvent(event));
    }

    @Test
    void handleContentAccountAddedEvent_valid() {
        UUID contentAccountId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        StreamList streamList = new StreamList(memberId);
        ContentAccountAddedEvent event = new ContentAccountAddedEvent(contentAccountId, memberId);
        Mockito.when(streamListRepository.getByMemberId(new MemberId(memberId))).thenReturn(Optional.of(streamList));

        assertDoesNotThrow(() -> underTest.handleContentAccountAddedEvent(event));
    }

    @Test
    void handleContentAccountRemovedEvent_createNew() {
        UUID contentAccountId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        ContentAccountRemovedEvent event = new ContentAccountRemovedEvent(contentAccountId, memberId);
        Mockito.when(streamListRepository.getByMemberId(new MemberId(memberId))).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> underTest.handleContentAccountRemovedEvent(event));
    }

    @Test
    void handleContentAccountRemovedEvent_valid() {
        UUID contentAccountId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        StreamList streamList = new StreamList(memberId);
        ContentAccountRemovedEvent event = new ContentAccountRemovedEvent(contentAccountId, memberId);
        Mockito.when(streamListRepository.getByMemberId(new MemberId(memberId))).thenReturn(Optional.of(streamList));

        assertDoesNotThrow(() -> underTest.handleContentAccountRemovedEvent(event));
    }
}