package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.AddWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.WatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.application.mapper.ListContentAccountMapper;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
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
class AddWatchingEntryServiceImplTest {

    @Mock
    private WatchingListRepository watchingListRepository;
    @Mock
    private ListContentAccountMapper listContentAccountMapper;

    @InjectMocks
    private AddWatchingEntryServiceImpl underTest;

    @Test
    void addWatchingEntry_noWatchingList_createNew() {
        UUID memberId = UUID.randomUUID();
        List<ContentAccountDto> contentAccountDtos = List.of(
                new ContentAccountDto("test", "name", ContentAccountPlatform.TWITCH)
        );
        AddWatchingEntryCommand command = AddWatchingEntryCommand
                .builder()
                .dto(new WatchingListEntryDto("name", contentAccountDtos))
                .memberId(memberId)
                .build();

        MemberId idNotFound = new MemberId(memberId);

        Mockito.when(watchingListRepository.getWatchingListByMemberId(idNotFound)).thenReturn(Optional.empty());
        Mockito.when(listContentAccountMapper.toContentAccountEntry(contentAccountDtos)).thenReturn(Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID()))));

        assertDoesNotThrow(() ->
                underTest.addWatchingEntry(command));
    }

    @Test
    void addWatchingEntry_emptyContentAccountIdSet_shouldThrow() {
        UUID memberId = UUID.randomUUID();
        List<ContentAccountDto> contentAccountDtos = List.of(
                new ContentAccountDto("test", "name", ContentAccountPlatform.TWITCH)
        );
        AddWatchingEntryCommand command = AddWatchingEntryCommand
                .builder()
                .dto(new WatchingListEntryDto("name", contentAccountDtos))
                .memberId(memberId)
                .build();

        MemberId idFound = new MemberId(memberId);

        WatchingList existingWatchingList = new WatchingList(UUID.randomUUID(), memberId, List.of(
                new WatchingListEntry(new ContentCreator("existing"), Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID()))))
        ));
        Mockito.when(watchingListRepository.getWatchingListByMemberId(idFound)).thenReturn(Optional.of(existingWatchingList));
        Mockito.when(listContentAccountMapper.toContentAccountEntry(contentAccountDtos)).thenReturn(Set.of());

        assertThrows(IllegalArgumentException.class, () ->
                underTest.addWatchingEntry(command));
    }

    @Test
    void addWatchingEntry_existingWatchingListEntry_shouldThrow() {
        UUID memberId = UUID.randomUUID();
        List<ContentAccountDto> contentAccountDtos = List.of(
                new ContentAccountDto("test", "name", ContentAccountPlatform.TWITCH)
        );
        AddWatchingEntryCommand command = AddWatchingEntryCommand
                .builder()
                .dto(new WatchingListEntryDto("name", contentAccountDtos))
                .memberId(memberId)
                .build();

        MemberId idFound = new MemberId(memberId);

        WatchingList existingWatchingList = new WatchingList(UUID.randomUUID(), memberId, List.of(
                new WatchingListEntry(new ContentCreator("name"), Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID()))))
        ));
        Mockito.when(watchingListRepository.getWatchingListByMemberId(idFound)).thenReturn(Optional.of(existingWatchingList));
        Mockito.when(listContentAccountMapper.toContentAccountEntry(contentAccountDtos)).thenReturn(Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID()))));

        assertThrows(IllegalArgumentException.class, () ->
                underTest.addWatchingEntry(command));
    }

    @Test
    void addWatchingEntry_existingWatchingListEntry_valid() {
        UUID memberId = UUID.randomUUID();
        List<ContentAccountDto> contentAccountDtos = List.of(
                new ContentAccountDto("test", "name", ContentAccountPlatform.TWITCH)
        );
        AddWatchingEntryCommand command = AddWatchingEntryCommand
                .builder()
                .dto(new WatchingListEntryDto("name", contentAccountDtos))
                .memberId(memberId)
                .build();


        MemberId idFound = new MemberId(memberId);

        WatchingList existingWatchingList = new WatchingList(UUID.randomUUID(), memberId, List.of(
                new WatchingListEntry(new ContentCreator("existing"), Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID()))))
        ));
        assertEquals(1, existingWatchingList.getWatchingListEntries().size());
        Mockito.when(watchingListRepository.getWatchingListByMemberId(idFound)).thenReturn(Optional.of(existingWatchingList));
        Mockito.when(listContentAccountMapper.toContentAccountEntry(contentAccountDtos)).thenReturn(Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID()))));

        assertDoesNotThrow(() ->
                underTest.addWatchingEntry(command));

        assertEquals(2, existingWatchingList.getWatchingListEntries().size());
        assertTrue(existingWatchingList.getWatchingListEntries().stream().anyMatch(entry -> entry.getContentCreatorName().equals("name")));
    }
}