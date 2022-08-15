package com.github.he305.contentcore.streamlist.infra.repository;

import com.github.he305.contentcore.shared.events.EventPublisher;
import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.infra.data.StreamListJpa;
import com.github.he305.contentcore.streamlist.infra.jpa.JpaStreamListRepository;
import com.github.he305.contentcore.streamlist.infra.mapper.StreamListJpaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StreamListRepositoryImplTest {

    @Mock
    private JpaStreamListRepository jpaStreamListRepository;
    @Mock
    private StreamListJpaMapper streamListJpaMapper;
    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private StreamListRepositoryImpl underTest;

    @Test
    void save() {
        StreamList data = new StreamList(UUID.randomUUID());
        StreamListJpa toReturn = new StreamListJpa();
        Mockito.when(streamListJpaMapper.toJpa(data)).thenReturn(toReturn);

        assertDoesNotThrow(() -> underTest.save(data));
    }

    @Test
    void getByMemberId_notFound() {
        UUID rawId = UUID.randomUUID();
        MemberId memberId = new MemberId(rawId);
        Mockito.when(jpaStreamListRepository.findByMemberId(rawId)).thenReturn(Optional.empty());

        Optional<StreamList> expected = Optional.empty();
        Optional<StreamList> actual = underTest.getByMemberId(memberId);

        assertEquals(expected, actual);
    }

    @Test
    void getByMemberId_found() {
        UUID rawId = UUID.randomUUID();
        MemberId memberId = new MemberId(rawId);
        StreamListJpa jpa = new StreamListJpa();
        StreamList expected = new StreamList(rawId);
        Mockito.when(jpaStreamListRepository.findByMemberId(rawId)).thenReturn(Optional.of(jpa));
        Mockito.when(streamListJpaMapper.toDomain(jpa)).thenReturn(expected);

        Optional<StreamList> actual = underTest.getByMemberId(memberId);
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }
}