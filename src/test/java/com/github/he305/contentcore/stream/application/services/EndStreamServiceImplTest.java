package com.github.he305.contentcore.stream.application.services;

import com.github.he305.contentcore.stream.application.commands.EndStreamCommand;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.entities.StreamData;
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
class EndStreamServiceImplTest {

    @Mock
    private StreamChannelRepository streamChannelRepository;

    @InjectMocks
    private EndStreamServiceImpl underTest;

    @Test
    void execute() {
        UUID id = UUID.randomUUID();
        EndStreamCommand command = new EndStreamCommand(id, LocalDateTime.now());
        StreamChannel channel = new StreamChannel(new StreamChannelContentAccountId(UUID.randomUUID()), StreamChannelPlatform.TWITCH);
        channel.addStreamData(new StreamData("name", "title", 0, LocalDateTime.now(ZoneOffset.UTC)));
        assertTrue(channel.isLive());

        Mockito.when(streamChannelRepository.getById(id)).thenReturn(channel);
        assertDoesNotThrow(() -> underTest.execute(command));
        assertFalse(channel.isLive());
    }
}