package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.CreateWatchingListCommand;
import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.WatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.application.mapper.ListContentAccountMapper;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CreateWatchingListServiceImplTest {

    @Mock
    private ListContentAccountMapper listContentAccountMapper;
    @Mock
    private WatchingListRepository watchingListRepository;

    @InjectMocks
    private CreateWatchingListServiceImpl underTest;

    @Test
    void execute_watchingListAlreadyExists_shouldThrow() {
        UUID id = UUID.randomUUID();
        CreateWatchingListCommand command = CreateWatchingListCommand
                .builder()
                .memberId(id)
                .watchingList(List.of(WatchingListEntryDto.builder().build()))
                .build();
        MemberId memberId = new MemberId(id);

        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.of(new WatchingList(UUID.randomUUID(), id)));
        assertThrows(IllegalArgumentException.class, () ->
                underTest.execute(command));
    }

    @Test
    void execute_emptyContentAccountId_shouldThrow() {
        UUID id = UUID.randomUUID();
        ContentAccountDto contentAccountDto = new ContentAccountDto("test", "name", ContentAccountPlatform.TWITCH);
        List<ContentAccountDto> accountDtos = List.of(contentAccountDto);
        WatchingListEntryDto dto = new WatchingListEntryDto(
                "name",
                accountDtos
        );
        CreateWatchingListCommand command = CreateWatchingListCommand
                .builder()
                .memberId(id)
                .watchingList(List.of(dto))
                .build();
        MemberId memberId = new MemberId(id);

        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.empty());
        Mockito.when(listContentAccountMapper.toContentAccountEntry(accountDtos)).thenReturn(Set.of());

        assertThrows(IllegalArgumentException.class, () ->
                underTest.execute(command));
    }

    @Test
    void execute_emptyWatchingList_shouldThrow() {
        UUID id = UUID.randomUUID();
        CreateWatchingListCommand command = CreateWatchingListCommand
                .builder()
                .memberId(id)
                .watchingList(List.of())
                .build();

        assertThrows(IllegalArgumentException.class, () ->
                underTest.execute(command));
    }

    @Test
    void execute_valid() {
        UUID id = UUID.randomUUID();
        ContentAccountDto contentAccountDto = new ContentAccountDto("test", "name", ContentAccountPlatform.TWITCH);
        List<ContentAccountDto> accountDtos = List.of(contentAccountDto);
        WatchingListEntryDto dto = new WatchingListEntryDto(
                "name",
                accountDtos
        );
        CreateWatchingListCommand command = CreateWatchingListCommand
                .builder()
                .memberId(id)
                .watchingList(List.of(dto))
                .build();
        MemberId memberId = new MemberId(id);

        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.empty());
        Mockito.when(listContentAccountMapper.toContentAccountEntry(accountDtos)).thenReturn(Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID()))));

        assertDoesNotThrow(() ->
                underTest.execute(command));
    }
}