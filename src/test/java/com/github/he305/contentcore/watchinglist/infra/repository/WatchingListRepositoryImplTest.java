package com.github.he305.contentcore.watchinglist.infra.repository;

import com.github.he305.contentcore.shared.events.EventPublisher;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListData;
import com.github.he305.contentcore.watchinglist.infra.jpa.JpaWatchingListRepository;
import com.github.he305.contentcore.watchinglist.infra.mapper.WatchingListMapper;
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
class WatchingListRepositoryImplTest {

    @Mock
    private JpaWatchingListRepository jpaWatchingListRepository;
    @Mock
    private WatchingListMapper watchingListMapper;
    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private WatchingListRepositoryImpl underTest;

    @Test
    void save() {
        WatchingList data = new WatchingList(UUID.randomUUID(), UUID.randomUUID());
        Mockito.when(watchingListMapper.toJpa(data)).thenReturn(WatchingListData.builder().build());
        assertDoesNotThrow(() -> underTest.save(data));
    }

    @Test
    void getWatchingListByMemberId_notFound() {
        UUID id = UUID.randomUUID();
        MemberId memberId = new MemberId(id);
        Mockito.when(jpaWatchingListRepository.getByMemberId(id)).thenReturn(Optional.empty());

        Optional<WatchingList> actual = underTest.getWatchingListByMemberId(memberId);
        assertTrue(actual.isEmpty());
    }

    @Test
    void getWatchingListByMemberId_valid() {
        UUID id = UUID.randomUUID();
        MemberId memberId = new MemberId(id);
        WatchingListData watchingListData = WatchingListData.builder().build();
        WatchingList expected = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        Mockito.when(jpaWatchingListRepository.getByMemberId(id)).thenReturn(Optional.ofNullable(watchingListData));
        Mockito.when(watchingListMapper.toDomain(watchingListData)).thenReturn(expected);

        Optional<WatchingList> actual = underTest.getWatchingListByMemberId(memberId);
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

}