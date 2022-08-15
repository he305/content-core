package com.github.he305.contentcore.streamlist.infra.mapper;

import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.domain.model.values.StreamChannelId;
import com.github.he305.contentcore.streamlist.infra.data.StreamChannelIdJpa;
import com.github.he305.contentcore.streamlist.infra.data.StreamListJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamListJpaMapperImplTest {

    private StreamListJpaMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new StreamListJpaMapperImpl();
    }

    @Test
    void toJpa() {
        UUID id = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        UUID streamChannelId = UUID.randomUUID();
        Set<StreamChannelId> set = Set.of(
                new StreamChannelId(streamChannelId)
        );
        StreamList list = new StreamList(id, new MemberId(memberId), set);

        Set<StreamChannelIdJpa> streamChannelIdJpaSet = Set.of(
                new StreamChannelIdJpa(streamChannelId)
        );
        StreamListJpa expected = new StreamListJpa(id, memberId, streamChannelIdJpaSet);

        StreamListJpa actual = underTest.toJpa(list);

        assertEquals(expected, actual);
    }

    @Test
    void toDomain() {
        UUID id = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        UUID streamChannelId = UUID.randomUUID();
        Set<StreamChannelId> set = Set.of(
                new StreamChannelId(streamChannelId)
        );
        StreamList expected = new StreamList(id, new MemberId(memberId), set);

        Set<StreamChannelIdJpa> streamChannelIdJpaSet = Set.of(
                new StreamChannelIdJpa(streamChannelId)
        );
        StreamListJpa jpa = new StreamListJpa(id, memberId, streamChannelIdJpaSet);

        StreamList actual = underTest.toDomain(jpa);

        assertEquals(expected, actual);
    }
}