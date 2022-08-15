package com.github.he305.contentcore.watchinglist.application.mapper.query;

import com.github.he305.contentcore.watchinglist.application.dto.query.GetContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.query.GetWatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
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
class GetWatchingListEntryMapperImplTest {

    @Mock
    private GetContentAccountDtoMapper getContentAccountDtoMapper;

    @InjectMocks
    private GetWatchingListEntryMapperImpl underTest;

    @Test
    void toDto() {
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry(
                UUID.randomUUID(),
                "test",
                new ContentAccountId(UUID.randomUUID()),
                Set.of(
                        new NotificationId(UUID.randomUUID())
                )
        );
        WatchingListEntry entry = new WatchingListEntry(
                UUID.randomUUID(),
                new ContentCreator("name"),
                Set.of(contentAccountEntry)
        );

        GetContentAccountDto getContentAccountDto = new GetContentAccountDto(
                "test",
                "name",
                ContentAccountPlatform.TWITCH,
                1
        );
        GetWatchingListEntryDto expected = new GetWatchingListEntryDto(
                "name",
                List.of(getContentAccountDto)
        );

        Mockito.when(getContentAccountDtoMapper.toDto(contentAccountEntry)).thenReturn(getContentAccountDto);
        GetWatchingListEntryDto actual = underTest.toDto(entry);
        assertEquals(expected, actual);
    }
}