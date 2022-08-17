package com.github.he305.contentcore.stream.domain.model.entities;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StreamTest {

    @Test
    void testEquals() {
        EqualsVerifier.forClass(Stream.class).suppress(Warning.ALL_FIELDS_SHOULD_BE_USED).verify();
    }


    @Test
    void getLastData_valid() {
        StreamData data1 = new StreamData("name", "title", 0, LocalDateTime.now(ZoneOffset.UTC).minus(3, ChronoUnit.SECONDS));
        StreamData data2 = new StreamData("sad", "title2", 1, LocalDateTime.now(ZoneOffset.UTC));
        assertNotEquals(data1, data2);

        Stream stream = new Stream(data1);
        StreamData firstData = stream.getLastData();
        assertEquals(data1, firstData);

        stream.addStreamData(data2);
        assertEquals(2, stream.getStreamDataList().size());

        StreamData secondData = stream.getLastData();
        assertEquals(data2, secondData);

        StreamData lateData = new StreamData("another", "naming", 2, LocalDateTime.now(ZoneOffset.UTC).minus(2, ChronoUnit.SECONDS));
        stream.addStreamData(lateData);

        StreamData thirdData = stream.getLastData();
        assertEquals(data2, thirdData);
    }

    @Test
    void getLastData_corruptedDatabaseData() {
        Stream stream = new Stream(UUID.randomUUID(), true, 0, LocalDateTime.now(ZoneOffset.UTC), LocalDateTime.now(ZoneOffset.UTC), Collections.emptyList());
        assertThrows(ContentCoreException.class, stream::getLastData);
    }
}