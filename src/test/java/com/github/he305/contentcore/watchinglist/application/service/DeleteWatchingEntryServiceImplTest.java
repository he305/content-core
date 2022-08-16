package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.DeleteWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.exceptions.WatchingListEntryNotExistsException;
import com.github.he305.contentcore.watchinglist.application.exceptions.WatchingListNotExistsException;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.model.values.WatchingListContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteWatchingEntryServiceImplTest {

    @Mock
    private WatchingListRepository watchingListRepository;

    @InjectMocks
    private DeleteWatchingEntryServiceImpl underTest;

    @Test
    void execute_noWatchingListFound() {
        UUID id = UUID.randomUUID();
        DeleteWatchingEntryCommand command = new DeleteWatchingEntryCommand(
                id,
                "smth"
        );
        MemberId memberId = new MemberId(id);
        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.empty());

        assertThrows(WatchingListNotExistsException.class, () ->
                underTest.execute(command));
    }

    @Test
    void execute_notEntryFound() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());
        String name = "testName";
        WatchingListContentAccountId watchingListContentAccountId = new WatchingListContentAccountId(UUID.randomUUID());
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", watchingListContentAccountId));

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, set));
        List<WatchingListEntry> entryList = watchingList.getWatchingListEntries();
        assertEquals(1, entryList.size());

        String deleteName = "some name";
        assertNotEquals(name, deleteName);

        UUID id = UUID.randomUUID();
        DeleteWatchingEntryCommand command = new DeleteWatchingEntryCommand(
                id,
                deleteName
        );
        MemberId memberId = new MemberId(id);
        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.of(watchingList));

        assertThrows(WatchingListEntryNotExistsException.class, () ->
                underTest.execute(command));
    }

    @Test
    void execute_valid() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());
        String name = "testName";
        WatchingListContentAccountId watchingListContentAccountId = new WatchingListContentAccountId(UUID.randomUUID());
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", watchingListContentAccountId));

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, set));
        List<WatchingListEntry> entryList = watchingList.getWatchingListEntries();
        assertEquals(1, entryList.size());

        UUID id = UUID.randomUUID();
        DeleteWatchingEntryCommand command = new DeleteWatchingEntryCommand(
                id,
                name
        );
        MemberId memberId = new MemberId(id);
        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.of(watchingList));

        assertDoesNotThrow(() ->
                underTest.execute(command));

        assertTrue(watchingList.getWatchingListEntries().isEmpty());
    }
}