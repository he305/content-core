package com.github.he305.contentcore.stream.application.services;

import com.github.he305.contentcore.stream.application.dto.StreamChannelDto;
import com.github.he305.contentcore.stream.application.dto.StreamChannelList;
import com.github.he305.contentcore.stream.application.mapper.StreamChannelDtoMapper;
import com.github.he305.contentcore.stream.application.query.GetStreamChannelByStatusQuery;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelStatus;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import com.github.he305.contentcore.stream.domain.repository.StreamChannelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GetStreamChannelByStatusServiceImplTest {

    @Mock
    private StreamChannelDtoMapper streamChannelDtoMapper;
    @Mock
    private StreamChannelRepository streamChannelRepository;

    @InjectMocks
    private GetStreamChannelByStatusServiceImpl underTest;

    @Test
    void execute() {
        GetStreamChannelByStatusQuery query = new GetStreamChannelByStatusQuery(StreamChannelStatus.OBSERVABLE);
        StreamChannel toReturn = new StreamChannel(new StreamChannelContentAccountId(UUID.randomUUID()), StreamChannelPlatform.TWITCH);
        StreamChannelDto dto = new StreamChannelDto(UUID.randomUUID(), "name", StreamChannelPlatform.TWITCH, true);
        Mockito.when(streamChannelRepository.getByStreamContentStatus(StreamChannelStatus.OBSERVABLE)).thenReturn(List.of(toReturn));
        Mockito.when(streamChannelDtoMapper.toDto(toReturn)).thenReturn(dto);

        StreamChannelList actual = underTest.execute(query);
        assertEquals(1, actual.getChannels().size());
        StreamChannelDto actualDto = actual.getChannels().get(0);
        assertEquals(dto.getChannelName(), actualDto.getChannelName());
        assertEquals(dto.getId(), actualDto.getId());
        assertEquals(dto.isLive(), actualDto.isLive());
        assertEquals(dto.getPlatform(), actualDto.getPlatform());
    }
}