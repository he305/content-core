package com.github.he305.contentcore.streamlist.domain.model;

import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.domain.model.values.StreamChannelId;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StreamListTest {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(StreamList.class).verify();
    }

    @Test
    void testJpaConstructor() {
        UUID id = UUID.randomUUID();
        MemberId memberId = new MemberId(UUID.randomUUID());

        Set<StreamChannelId> set = Set.of(
                new StreamChannelId(UUID.randomUUID()),
                new StreamChannelId(UUID.randomUUID()),
                new StreamChannelId(UUID.randomUUID())
        );

        StreamList streamList = new StreamList(id, memberId, set);
        assertEquals(id, streamList.getId());
        assertEquals(memberId, streamList.getMemberId());
        assertEquals(set, streamList.getStreamChannelIdList());
    }

    @Test
    void addStreamChannelId() {
        StreamList streamList = new StreamList(UUID.randomUUID());
        assertTrue(streamList.getStreamChannelIdList().isEmpty());

        UUID streamChannelId = UUID.randomUUID();
        StreamChannelId id = new StreamChannelId(streamChannelId);
        streamList.addStreamChannelId(streamChannelId);
        assertEquals(1, streamList.getStreamChannelIdList().size());
        assertTrue(streamList.getStreamChannelIdList().contains(id));

        // Second add the same
        streamList.addStreamChannelId(streamChannelId);
        assertEquals(1, streamList.getStreamChannelIdList().size());
        assertTrue(streamList.getStreamChannelIdList().contains(id));
    }

    @Test
    void removeStreamChannelId() {
        StreamList streamList = new StreamList(UUID.randomUUID());
        assertTrue(streamList.getStreamChannelIdList().isEmpty());

        UUID streamChannelId = UUID.randomUUID();
        StreamChannelId id = new StreamChannelId(streamChannelId);
        streamList.addStreamChannelId(streamChannelId);
        assertEquals(1, streamList.getStreamChannelIdList().size());
        assertTrue(streamList.getStreamChannelIdList().contains(id));

        // Remove not-existing
        streamList.removeStreamChannelId(UUID.randomUUID());
        assertEquals(1, streamList.getStreamChannelIdList().size());
        assertTrue(streamList.getStreamChannelIdList().contains(id));

        // Remove existing
        streamList.removeStreamChannelId(streamChannelId);
        assertEquals(0, streamList.getStreamChannelIdList().size());
        assertFalse(streamList.getStreamChannelIdList().contains(id));

        // Remove one more time
        streamList.removeStreamChannelId(streamChannelId);
        assertEquals(0, streamList.getStreamChannelIdList().size());
        assertFalse(streamList.getStreamChannelIdList().contains(id));
    }
}