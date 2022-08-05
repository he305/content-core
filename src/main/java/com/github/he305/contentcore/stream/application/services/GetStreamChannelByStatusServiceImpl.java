package com.github.he305.contentcore.stream.application.services;

import com.github.he305.contentcore.stream.application.dto.StreamChannelDto;
import com.github.he305.contentcore.stream.application.dto.StreamChannelList;
import com.github.he305.contentcore.stream.application.mapper.StreamChannelDtoMapper;
import com.github.he305.contentcore.stream.application.query.GetStreamChannelByStatusQuery;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.repository.StreamChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetStreamChannelByStatusServiceImpl implements GetStreamChannelByStatusService {
    private final StreamChannelDtoMapper streamChannelDtoMapper;
    private final StreamChannelRepository streamChannelRepository;

    @Override
    public StreamChannelList execute(GetStreamChannelByStatusQuery query) {
        List<StreamChannel> streamChannelList = streamChannelRepository.getByStreamContentStatus(query.getStatus());
        List<StreamChannelDto> streamChannelDtoList = streamChannelList.stream().map(streamChannelDtoMapper::toDto).collect(Collectors.toList());
        return new StreamChannelList(streamChannelDtoList);
    }
}
