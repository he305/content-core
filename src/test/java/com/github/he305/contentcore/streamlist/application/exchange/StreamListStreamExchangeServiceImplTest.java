package com.github.he305.contentcore.streamlist.application.exchange;

import com.github.he305.contentcore.stream.domain.model.entities.StreamData;
import com.github.he305.contentcore.streamlist.application.dto.StreamListEntryLastDataDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StreamListStreamExchangeServiceImplTest {

    @Mock
    private com.github.he305.contentcore.stream.application.exchange.StreamExchangeService streamExchangeService;

    @InjectMocks
    private StreamListStreamExchangeServiceImpl underTest;

    @Test
    void getIsLive() {
        UUID id = UUID.randomUUID();
        boolean isLive = true;

        Mockito.when(streamExchangeService.getIsLive(id)).thenReturn(isLive);
        boolean actual = underTest.getIsLive(id);
        assertEquals(isLive, actual);
    }

    @Test
    void getLastData_nullStreamData() {
        UUID id = UUID.randomUUID();
        Mockito.when(streamExchangeService.getLastLiveStreamData(id)).thenReturn(null);
        StreamListEntryLastDataDto expected = StreamListEntryLastDataDto.empty();

        StreamListEntryLastDataDto actual = underTest.getLastData(id);
        assertEquals(expected, actual);
    }

    @Test
    void getLastData_someData() {
        UUID id = UUID.randomUUID();
        StreamData streamData = new StreamData("game name", "title", 322, LocalDateTime.now(ZoneOffset.UTC));
        Mockito.when(streamExchangeService.getLastLiveStreamData(id)).thenReturn(streamData);
        StreamListEntryLastDataDto expected = new StreamListEntryLastDataDto(
                "title",
                "game name",
                322
        );

        StreamListEntryLastDataDto actual = underTest.getLastData(id);
        assertEquals(expected, actual);
    }
}