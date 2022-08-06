package com.github.he305.contentcore.stream.infra.mapper;

import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.entities.Stream;
import com.github.he305.contentcore.stream.domain.model.entities.StreamData;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelStatus;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import com.github.he305.contentcore.stream.infra.data.StreamChannelJpa;
import com.github.he305.contentcore.stream.infra.data.StreamDataJpa;
import com.github.he305.contentcore.stream.infra.data.StreamJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO: write tests for every mapper
@SpringBootTest
class StreamChannelJpaMapperImplTest {

    @Autowired
    private StreamChannelJpaMapper underTest;

    @Test
    void toJpa() {
        UUID channelId = UUID.randomUUID();
        UUID contentAccountId = UUID.randomUUID();
        UUID streamId = UUID.randomUUID();
        UUID streamDataId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        StreamData streamData = new StreamData(streamDataId, "name", "title", 30, time);
        Stream stream = new Stream(
                streamId,
                true,
                30,
                time,
                time,
                List.of(streamData)
        );

        StreamChannel streamChannel = new StreamChannel(
                channelId,
                new StreamChannelContentAccountId(contentAccountId),
                StreamChannelPlatform.TWITCH,
                List.of(stream),
                StreamChannelStatus.OBSERVABLE
        );

        StreamChannelJpa expected = new StreamChannelJpa(
                channelId,
                contentAccountId,
                StreamChannelPlatform.TWITCH,
                Collections.emptyList(),
                StreamChannelStatus.OBSERVABLE
        );
        StreamJpa streamJpa = new StreamJpa(
                streamId,
                30,
                time,
                time,
                true,
                expected,
                Collections.emptyList()
        );
        StreamDataJpa streamDataJpa = new StreamDataJpa(
                streamDataId,
                "name",
                "title",
                30,
                time,
                streamJpa
        );
        streamJpa.setStreamDatas(new ArrayList<>(List.of(streamDataJpa)));
        expected.setStreams(new ArrayList<>(List.of(streamJpa)));

        StreamChannelJpa actual = underTest.toJpa(streamChannel);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getPlatform(), actual.getPlatform());
        assertEquals(expected.getContentAccountId(), actual.getContentAccountId());
    }

    @Test
    void toDomain() {
        UUID channelId = UUID.randomUUID();
        UUID contentAccountId = UUID.randomUUID();
        UUID streamId = UUID.randomUUID();
        UUID streamDataId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        StreamData streamData = new StreamData(streamDataId, "name", "title", 30, time);
        Stream stream = new Stream(
                streamId,
                true,
                30,
                time,
                time,
                List.of(streamData)
        );

        StreamChannel expected = new StreamChannel(
                channelId,
                new StreamChannelContentAccountId(contentAccountId),
                StreamChannelPlatform.TWITCH,
                List.of(stream),
                StreamChannelStatus.OBSERVABLE
        );

        StreamChannelJpa data = new StreamChannelJpa(
                channelId,
                contentAccountId,
                StreamChannelPlatform.TWITCH,
                Collections.emptyList(),
                StreamChannelStatus.OBSERVABLE
        );
        StreamJpa streamJpa = new StreamJpa(
                streamId,
                30,
                time,
                time,
                true,
                data,
                Collections.emptyList()
        );
        StreamDataJpa streamDataJpa = new StreamDataJpa(
                streamDataId,
                "name",
                "title",
                30,
                time,
                streamJpa
        );
        streamJpa.setStreamDatas(new ArrayList<>(List.of(streamDataJpa)));
        data.setStreams(new ArrayList<>(List.of(streamJpa)));

        StreamChannel actual = underTest.toDomain(data);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getStreamChannelContentAccountId(), actual.getStreamChannelContentAccountId());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getPlatform(), actual.getPlatform());
    }
}