package com.github.he305.contentcore.stream.application.web.controller;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;
import com.github.he305.contentcore.stream.application.commands.EndStreamCommand;
import com.github.he305.contentcore.stream.application.commands.PostStreamerDataCommand;
import com.github.he305.contentcore.stream.application.dto.StreamChannelDto;
import com.github.he305.contentcore.stream.application.dto.StreamChannelList;
import com.github.he305.contentcore.stream.application.dto.StreamDataDto;
import com.github.he305.contentcore.stream.application.dto.StreamEndDto;
import com.github.he305.contentcore.stream.application.query.GetStreamChannelByStatusQuery;
import com.github.he305.contentcore.stream.application.services.EndStreamService;
import com.github.he305.contentcore.stream.application.services.GetStreamChannelByStatusService;
import com.github.he305.contentcore.stream.application.services.PostStreamerDataService;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class StreamChannelControllerTest {

    @Mock
    private GetStreamChannelByStatusService getStreamChannelByStatusService;
    @Mock
    private PostStreamerDataService postStreamerDataService;
    @Mock
    private EndStreamService endStreamService;

    @InjectMocks
    private StreamChannelController underTest;

    @Test
    void getStreamChannelsByStatus_error() {
        StreamChannelStatus status = StreamChannelStatus.OBSERVABLE;
        Mockito.when(getStreamChannelByStatusService.execute(new GetStreamChannelByStatusQuery(status))).thenThrow(ContentCoreException.class);

        assertThrows(ContentCoreException.class, () -> underTest.getObservableStreamChannels());
    }

    @Test
    void getStreamChannelsByStatus_valid() {
        StreamChannelStatus status = StreamChannelStatus.OBSERVABLE;
        StreamChannelList list = new StreamChannelList(List.of(new StreamChannelDto(UUID.randomUUID(), "name", StreamChannelPlatform.TWITCH, false)));
        Mockito.when(getStreamChannelByStatusService.execute(new GetStreamChannelByStatusQuery(status))).thenReturn(list);

        StreamChannelList actual = underTest.getObservableStreamChannels();
        assertNotNull(actual);
        assertEquals(list.getChannels().size(), actual.getChannels().size());
    }

    @Test
    void postStreamData_error() {
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        StreamDataDto dto = new StreamDataDto(id, "name", "title", 0, time);
        doThrow(new ContentCoreException("smth")).when(postStreamerDataService).execute(new PostStreamerDataCommand(
                id,
                "name",
                "title",
                0,
                time));

        assertThrows(ContentCoreException.class, () -> underTest.postStreamData(dto));
    }

    @Test
    void postStreamData_valid() {
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        StreamDataDto dto = new StreamDataDto(id, "name", "title", 0, time);

        assertDoesNotThrow(() -> underTest.postStreamData(dto));
    }

    @Test
    void endStream_error() {
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        StreamEndDto dto = new StreamEndDto(id, time);
        doThrow(new ContentCoreException("smth")).when(endStreamService).execute(new EndStreamCommand(id, time));

        assertThrows(ContentCoreException.class, () -> underTest.endStream(dto));
    }

    @Test
    void endStream_valid() {
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        StreamEndDto dto = new StreamEndDto(id, time);

        assertDoesNotThrow(() -> underTest.endStream(dto));
    }
}