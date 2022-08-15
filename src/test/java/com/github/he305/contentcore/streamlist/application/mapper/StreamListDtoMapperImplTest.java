package com.github.he305.contentcore.streamlist.application.mapper;

import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.streamlist.application.dto.StreamListDto;
import com.github.he305.contentcore.streamlist.application.dto.StreamListEntryDto;
import com.github.he305.contentcore.streamlist.application.dto.StreamListEntryLastDataDto;
import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.domain.model.values.StreamChannelId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StreamListDtoMapperImplTest {

    @Mock
    private StreamListEntryDtoMapper streamListEntryDtoMapper;

    @InjectMocks
    private StreamListDtoMapperImpl underTest;

    @Test
    void toDto() {
        StreamChannelId streamChannelId = new StreamChannelId(UUID.randomUUID());
        StreamList list = new StreamList(
                UUID.randomUUID(),
                new MemberId(UUID.randomUUID()),
                Set.of(streamChannelId)
        );

        StreamListEntryDto entryDto = new StreamListEntryDto("name", StreamChannelPlatform.TWITCH, true, StreamListEntryLastDataDto.empty());
        Mockito.when(streamListEntryDtoMapper.toDto(streamChannelId)).thenReturn(entryDto);

        StreamListDto expected = new StreamListDto(List.of(entryDto));
        StreamListDto actual = underTest.toDto(list);
        assertEquals(expected, actual);
    }
}