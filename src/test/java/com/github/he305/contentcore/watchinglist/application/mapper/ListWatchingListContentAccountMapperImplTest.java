package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import com.github.he305.contentcore.watchinglist.domain.model.values.WatchingListContentAccountId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ListWatchingListContentAccountMapperImplTest {

    @Mock
    private ContentAccountDtoIdMapper contentAccountDtoIdMapper;

    @InjectMocks
    private ListContentAccountMapperImpl underTest;

    @Test
    void toContentAccountIdSet() {
        ContentAccountDto data = new ContentAccountDto("test", "name", ContentAccountPlatform.TWITCH);
        WatchingListContentAccountId mocked = new WatchingListContentAccountId(UUID.randomUUID());
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry("test", mocked);
        Set<ContentAccountEntry> expected = Set.of(contentAccountEntry);
        Mockito.when(contentAccountDtoIdMapper.toContentAccountEntry(data)).thenReturn(contentAccountEntry);

        Set<ContentAccountEntry> actual = underTest.toContentAccountEntry(List.of(data));
        assertEquals(expected, actual);
    }

    @Test
    void toContentAccountDtoList() {
        WatchingListContentAccountId data = new WatchingListContentAccountId(UUID.randomUUID());
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry("test", data);
        ContentAccountDto mocked = new ContentAccountDto("test", "name", ContentAccountPlatform.TWITCH);
        List<ContentAccountDto> expected = List.of(mocked);
        Mockito.when(contentAccountDtoIdMapper.toContentAccountDto(contentAccountEntry)).thenReturn(mocked);

        List<ContentAccountDto> actual = underTest.toContentAccountDtoList(Set.of(contentAccountEntry));
        assertEquals(expected, actual);
    }
}