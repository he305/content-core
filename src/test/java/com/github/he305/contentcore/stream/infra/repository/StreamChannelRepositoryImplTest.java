package com.github.he305.contentcore.stream.infra.repository;

import com.github.he305.contentcore.shared.events.EventPublisher;
import com.github.he305.contentcore.stream.domain.exceptions.StreamChannelNotFoundException;
import com.github.he305.contentcore.stream.domain.exceptions.StreamChannelWithContentAccountIdNotFoundException;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelStatus;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import com.github.he305.contentcore.stream.infra.data.StreamChannelJpa;
import com.github.he305.contentcore.stream.infra.jpa.JpaStreamChannelRepository;
import com.github.he305.contentcore.stream.infra.mapper.StreamChannelJpaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StreamChannelRepositoryImplTest {

    @Mock
    private JpaStreamChannelRepository jpaStreamChannelRepository;
    @Mock
    private StreamChannelJpaMapper streamChannelJpaMapper;
    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private StreamChannelRepositoryImpl underTest;

    @Test
    void save() {
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(UUID.randomUUID()), StreamChannelPlatform.TWITCH);
        StreamChannelJpa streamChannelJpa = StreamChannelJpa.builder().build();
        Mockito.when(streamChannelJpaMapper.toJpa(streamChannel)).thenReturn(streamChannelJpa);
        assertDoesNotThrow(() -> underTest.save(streamChannel));
    }

    @Test
    void getByContentAccountId_valid() {
        StreamChannelContentAccountId id = new StreamChannelContentAccountId(UUID.randomUUID());
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(UUID.randomUUID()), StreamChannelPlatform.TWITCH);
        StreamChannelJpa streamChannelJpa = StreamChannelJpa.builder().build();
        Mockito.when(jpaStreamChannelRepository.findByContentAccountId(id.getId())).thenReturn(Optional.ofNullable(streamChannelJpa));
        Mockito.when(streamChannelJpaMapper.toDomain(streamChannelJpa)).thenReturn(streamChannel);

        StreamChannel actual = underTest.getByContentAccountId(id);
        assertEquals(streamChannel, actual);
    }

    @Test
    void getByContentAccountId_notFound_shouldThrow() {
        StreamChannelContentAccountId id = new StreamChannelContentAccountId(UUID.randomUUID());
        Mockito.when(jpaStreamChannelRepository.findByContentAccountId(id.getId())).thenReturn(Optional.empty());
        assertThrows(StreamChannelWithContentAccountIdNotFoundException.class, () ->
                underTest.getByContentAccountId(id));
    }

    @Test
    void getById() {
        UUID id = UUID.randomUUID();
        StreamChannelJpa jpa = StreamChannelJpa.builder().build();
        StreamChannel expected = new StreamChannel(new StreamChannelContentAccountId(id), StreamChannelPlatform.TWITCH);
        Mockito.when(jpaStreamChannelRepository.findById(id)).thenReturn(Optional.ofNullable(jpa));
        Mockito.when(streamChannelJpaMapper.toDomain(jpa)).thenReturn(expected);
        assertDoesNotThrow(() -> underTest.getById(id));
    }

    @Test
    void getById_empty() {
        UUID id = UUID.randomUUID();
        Mockito.when(jpaStreamChannelRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(StreamChannelNotFoundException.class, () -> underTest.getById(id));
    }

    @Test
    void getByStreamContentStatus() {
        StreamChannelStatus status = StreamChannelStatus.OBSERVABLE;
        StreamChannelJpa jpa = StreamChannelJpa.builder().build();
        StreamChannel expected = new StreamChannel(new StreamChannelContentAccountId(UUID.randomUUID()), StreamChannelPlatform.TWITCH);
        Mockito.when(jpaStreamChannelRepository.findByStatus(status)).thenReturn(List.of(jpa));
        Mockito.when(streamChannelJpaMapper.toDomain(jpa)).thenReturn(expected);

        assertDoesNotThrow(() -> underTest.getByStreamContentStatus(status));
    }
}