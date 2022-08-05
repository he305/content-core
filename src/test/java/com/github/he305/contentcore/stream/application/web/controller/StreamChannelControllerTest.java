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
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        ResponseEntity<StreamChannelList> expected = ResponseEntity.internalServerError().build();
        StreamChannelStatus status = StreamChannelStatus.OBSERVABLE;
        Mockito.when(getStreamChannelByStatusService.execute(new GetStreamChannelByStatusQuery(status))).thenThrow(ContentCoreException.class);

        ResponseEntity<StreamChannelList> actual = underTest.getStreamChannelsByStatus(status);
        assertEquals(expected, actual);
    }

    @Test
    void getStreamChannelsByStatus_valid() {
        StreamChannelStatus status = StreamChannelStatus.OBSERVABLE;
        StreamChannelList list = new StreamChannelList(List.of(new StreamChannelDto(UUID.randomUUID(), "name", StreamChannelPlatform.TWITCH, false)));
        ResponseEntity<StreamChannelList> expected = ResponseEntity.ok(list);
        Mockito.when(getStreamChannelByStatusService.execute(new GetStreamChannelByStatusQuery(status))).thenReturn(list);

        ResponseEntity<StreamChannelList> actual = underTest.getStreamChannelsByStatus(status);
        assertEquals(expected.getStatusCode(), actual.getStatusCode());
        assertNotNull(expected.getBody());
        assertEquals(expected.getBody().getChannels().size(), expected.getBody().getChannels().size());
    }

    @Test
    void postStreamData_error() {
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        StreamDataDto dto = new StreamDataDto(id, "name", "title", 0, time);
        doThrow(new ContentCoreException("smth")).when(postStreamerDataService).execute(new PostStreamerDataCommand(
                id,
                "name",
                "title",
                0,
                time));

        ResponseEntity<Void> expected = ResponseEntity.internalServerError().build();
        ResponseEntity<Void> actual = underTest.postStreamData(dto);

        assertEquals(expected, actual);
    }

    @Test
    void postStreamData_valid() {
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        StreamDataDto dto = new StreamDataDto(id, "name", "title", 0, time);

        ResponseEntity<Void> expected = ResponseEntity.ok().build();
        ResponseEntity<Void> actual = underTest.postStreamData(dto);

        assertEquals(expected, actual);
    }

    @Test
    void endStream_error() {
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        StreamEndDto dto = new StreamEndDto(id, time);
        doThrow(new ContentCoreException("smth")).when(endStreamService).execute(new EndStreamCommand(id, time));

        ResponseEntity<Void> expected = ResponseEntity.internalServerError().build();
        ResponseEntity<Void> actual = underTest.endStream(dto);
        assertEquals(expected, actual);
    }

    @Test
    void endStream_valid() {
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        StreamEndDto dto = new StreamEndDto(id, time);

        ResponseEntity<Void> expected = ResponseEntity.ok().build();
        ResponseEntity<Void> actual = underTest.endStream(dto);
        assertEquals(expected, actual);
    }
}