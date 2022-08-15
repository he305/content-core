package com.github.he305.contentcore.streamlist.application.services;

import com.github.he305.contentcore.shared.events.EventPublisher;
import com.github.he305.contentcore.streamlist.application.dto.StreamListDto;
import com.github.he305.contentcore.streamlist.application.mapper.StreamListDtoMapper;
import com.github.he305.contentcore.streamlist.application.query.GetStreamListQuery;
import com.github.he305.contentcore.streamlist.domain.exceptions.StreamListNotFoundException;
import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.domain.repository.StreamListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GetStreamListServiceImplTest {

    @Mock
    private StreamListRepository streamListRepository;
    @Mock
    private StreamListDtoMapper streamListDtoMapper;
    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private GetStreamListServiceImpl underTest;

    @Test
    void execute_noStreamListFound() {
        UUID id = UUID.randomUUID();
        GetStreamListQuery query = new GetStreamListQuery(id);
        MemberId memberId = new MemberId(id);
        Mockito.when(streamListRepository.getByMemberId(memberId)).thenReturn(Optional.empty());
        assertThrows(StreamListNotFoundException.class, () ->
                underTest.execute(query));
    }

    @Test
    void execute_valid() {
        UUID id = UUID.randomUUID();
        GetStreamListQuery query = new GetStreamListQuery(id);
        MemberId memberId = new MemberId(id);
        StreamList streamList = new StreamList(id);
        Mockito.when(streamListRepository.getByMemberId(memberId)).thenReturn(Optional.of(streamList));

        StreamListDto expected = new StreamListDto(Collections.emptyList());
        Mockito.when(streamListDtoMapper.toDto(streamList)).thenReturn(expected);

        StreamListDto actual = underTest.execute(query);
        assertEquals(expected, actual);
    }
}