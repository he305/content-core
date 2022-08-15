package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.dto.MemberIdDto;
import com.github.he305.contentcore.watchinglist.application.dto.query.GetContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.query.GetWatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.application.mapper.query.GetWatchingListEntryMapper;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GetWatchingListServiceImplTest {

    @Mock
    private GetWatchingListEntryMapper getWatchingListEntryMapper;
    @Mock
    private WatchingListRepository watchingListRepository;

    @InjectMocks
    private GetWatchingListServiceImpl underTest;

    @Test
    void getWatchingList_watchingListNotFound_returnEmpty() {
        UUID id = UUID.randomUUID();
        MemberId memberId = new MemberId(id);
        MemberIdDto dto = new MemberIdDto(id);

        GetWatchingListQuery expected = new GetWatchingListQuery(
                Collections.emptyList()
        );
        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.empty());

        GetWatchingListQuery actual = underTest.getWatchingList(dto);
        assertEquals(expected, actual);
    }

    @Test
    void getWatchingList_valid() {
        UUID id = UUID.randomUUID();
        MemberId memberId = new MemberId(id);
        MemberIdDto dto = new MemberIdDto(id);

        ContentAccountId contentAccountId = new ContentAccountId(UUID.randomUUID());
        WatchingListEntry entry = new WatchingListEntry(UUID.randomUUID(), new ContentCreator("name"), Set.of());
        WatchingList watchingList = new WatchingList(
                UUID.randomUUID(),
                id,
                List.of(entry)
        );

        GetWatchingListEntryDto expectedDto = new GetWatchingListEntryDto("name", List.of(
                new GetContentAccountDto("alias", "name", ContentAccountPlatform.TWITCH, 1)
        ));
        GetWatchingListQuery expected = new GetWatchingListQuery(
                List.of(expectedDto)
        );

        Mockito.when(watchingListRepository.getWatchingListByMemberId(memberId)).thenReturn(Optional.of(watchingList));
        Mockito.when(getWatchingListEntryMapper.toDto(entry)).thenReturn(expectedDto);

        GetWatchingListQuery actual = underTest.getWatchingList(dto);
        assertEquals(expected, actual);
    }
}