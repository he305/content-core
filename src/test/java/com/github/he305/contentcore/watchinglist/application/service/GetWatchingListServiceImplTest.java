package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.MemberIdDto;
import com.github.he305.contentcore.watchinglist.application.dto.WatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.application.mapper.ListContentAccountMapper;
import com.github.he305.contentcore.watchinglist.application.query.GetWatchingListQuery;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GetWatchingListServiceImplTest {

    @Mock
    private ListContentAccountMapper listContentAccountMapper;
    @Mock
    private WatchingListRepository watchingListRepository;

    @InjectMocks
    private GetWatchingListServiceImpl underTest;

    @Test
    void getWatchingList_watchingListNotFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        MemberId memberId = new MemberId(id);
        MemberIdDto dto = new MemberIdDto(id);

        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () ->
                underTest.getWatchingList(dto));
    }

    @Test
    void getWatchingList_valid() {
        UUID id = UUID.randomUUID();
        MemberId memberId = new MemberId(id);
        MemberIdDto dto = new MemberIdDto(id);

        ContentAccountId contentAccountId = new ContentAccountId(UUID.randomUUID());
        WatchingListEntry entry = new WatchingListEntry(UUID.randomUUID(), new ContentCreator("name"), Set.of(contentAccountId));
        WatchingList watchingList = new WatchingList(
                UUID.randomUUID(),
                id,
                List.of(entry)
        );

        WatchingListEntryDto expectedDto = new WatchingListEntryDto("name", List.of(
                new ContentAccountDto("name", ContentAccountPlatform.TWITCH)
        ));
        GetWatchingListQuery expected = new GetWatchingListQuery(
                id,
                List.of(expectedDto)
        );

        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.of(watchingList));
        Mockito.when(listContentAccountMapper.toContentAccountDtoList(Set.of(contentAccountId))).thenReturn(List.of(new ContentAccountDto("name", ContentAccountPlatform.TWITCH)));

        GetWatchingListQuery actual = underTest.getWatchingList(dto);
        assertEquals(expected, actual);
    }
}