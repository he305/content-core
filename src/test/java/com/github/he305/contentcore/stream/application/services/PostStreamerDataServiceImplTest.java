package com.github.he305.contentcore.stream.application.services;

import com.github.he305.contentcore.stream.application.commands.PostStreamerDataCommand;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import com.github.he305.contentcore.stream.domain.repository.StreamChannelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostStreamerDataServiceImplTest {

    @Mock
    private StreamChannelRepository streamChannelRepository;

    @InjectMocks
    private PostStreamerDataServiceImpl underTest;

    @Test
    void execute() {
        UUID id = UUID.randomUUID();
        PostStreamerDataCommand command = new PostStreamerDataCommand(
                id,
                "name",
                "title",
                0,
                LocalDateTime.now(ZoneOffset.UTC)
        );

        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(UUID.randomUUID()), StreamChannelPlatform.TWITCH);
        assertFalse(streamChannel.isLive());
        assertTrue(streamChannel.getStreams().isEmpty());
        Mockito.when(streamChannelRepository.getById(id)).thenReturn(streamChannel);

        assertDoesNotThrow(() -> underTest.execute(command));
        assertTrue(streamChannel.isLive());
        assertEquals(1, streamChannel.getStreams().size());
    }
}