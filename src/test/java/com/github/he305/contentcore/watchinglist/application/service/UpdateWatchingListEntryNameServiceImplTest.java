package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.UpdateWatchingListEntryNameCommand;
import com.github.he305.contentcore.watchinglist.application.exceptions.UpdateWatchingListEntryEqualNamesException;
import com.github.he305.contentcore.watchinglist.application.exceptions.WatchingListNotExistsException;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UpdateWatchingListEntryNameServiceImplTest {

    @Mock
    private WatchingListRepository watchingListRepository;
    @InjectMocks
    private UpdateWatchingListEntryNameServiceImpl underTest;

    @Test
    void execute_namesEqual_shouldThrow() {
        UUID id = UUID.randomUUID();
        UpdateWatchingListEntryNameCommand command = new UpdateWatchingListEntryNameCommand(
                id,
                "equal",
                "equal"
        );
        assertThrows(UpdateWatchingListEntryEqualNamesException.class, () ->
                underTest.execute(command));
    }

    @Test
    void execute_notFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        UpdateWatchingListEntryNameCommand command = new UpdateWatchingListEntryNameCommand(
                id,
                "asd",
                "sadad"
        );

        MemberId memberId = new MemberId(id);
        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.empty());
        assertThrows(WatchingListNotExistsException.class, () ->
                underTest.execute(command));
    }

    @Test
    void execute_valid() {
        UUID id = UUID.randomUUID();
        UpdateWatchingListEntryNameCommand command = new UpdateWatchingListEntryNameCommand(
                id,
                "oldName",
                "newName"
        );

        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID(), List.of(
                new WatchingListEntry(new ContentCreator("oldName"))
        ));
        MemberId memberId = new MemberId(id);
        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.of(watchingList));

        assertDoesNotThrow(() -> underTest.execute(command));
    }
}