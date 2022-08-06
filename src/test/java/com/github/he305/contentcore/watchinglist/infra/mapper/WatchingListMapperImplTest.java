package com.github.he305.contentcore.watchinglist.infra.mapper;

import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WatchingListMapperImplTest {

    @Mock
    private WatchingListEntryMapper watchingListEntryMapper;

    @InjectMocks
    private WatchingListMapperImpl underTest;

    @Test
    void toJpa() {
        WatchingList data = new WatchingList(UUID.randomUUID(), UUID.randomUUID());
        WatchingListData expected = new WatchingListData(data.getId(), data.getMemberId().getId(), Collections.emptyList());
        WatchingListData actual = underTest.toJpa(data);
        assertEquals(expected, actual);
    }

    @Test
    void toDomain() {
        WatchingListData data = new WatchingListData(UUID.randomUUID(), UUID.randomUUID(), Collections.emptyList());
        WatchingList expected = new WatchingList(data.getId(), data.getMemberId());

        WatchingList actual = underTest.toDomain(data);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getMemberId(), actual.getMemberId());
    }
}