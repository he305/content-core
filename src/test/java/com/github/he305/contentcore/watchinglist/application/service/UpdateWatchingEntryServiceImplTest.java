package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.UpdateWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.WatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.application.mapper.ListContentAccountMapper;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
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
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateWatchingEntryServiceImplTest {

    @Mock
    private WatchingListRepository watchingListRepository;
    @Mock
    private ListContentAccountMapper listContentAccountMapper;

    @InjectMocks
    private UpdateWatchingEntryServiceImpl underTest;

    @Test
    void updateWatchingEntry_noWatchingList_shouldThrow() {
        UUID id = UUID.randomUUID();
        MemberId memberId = new MemberId(id);
        UpdateWatchingEntryCommand command = UpdateWatchingEntryCommand
                .builder()
                .memberId(id)
                .build();

        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () ->
                underTest.updateWatchingEntry(command));
    }


    @Test
    void updateWatchingEntry_valid() {
        UUID id = UUID.randomUUID();
        MemberId memberId = new MemberId(id);

        ContentAccountId newContentAccountId = new ContentAccountId(UUID.randomUUID());
        ContentAccountDto contentAccountDto = new ContentAccountDto("name", ContentAccountPlatform.TWITCH);
        WatchingListEntryDto dto = new WatchingListEntryDto(
                "name",
                List.of(contentAccountDto)
        );

        UpdateWatchingEntryCommand command = UpdateWatchingEntryCommand
                .builder()
                .dto(dto)
                .memberId(id)
                .build();

        WatchingListEntry entry = new WatchingListEntry(
                new ContentCreator("name"),
                Set.of(new ContentAccountId(UUID.randomUUID()))
        );

        WatchingList watchingList = new WatchingList(
                UUID.randomUUID(),
                id,
                List.of(entry)
        );
        assertEquals(1, watchingList.getWatchingListEntries().size());
        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.of(watchingList));
        Mockito.when(listContentAccountMapper.toContentAccountIdSet(List.of(contentAccountDto))).thenReturn(Set.of(newContentAccountId));
        assertDoesNotThrow(() ->
                underTest.updateWatchingEntry(command));

        assertEquals(1, watchingList.getWatchingListEntries().size());
        WatchingListEntry updatedEntry = watchingList.getWatchingListEntries().get(0);
        assertEquals("name", updatedEntry.getContentCreatorName());
        assertEquals(Set.of(newContentAccountId), updatedEntry.getContentAccountSet());
    }

}