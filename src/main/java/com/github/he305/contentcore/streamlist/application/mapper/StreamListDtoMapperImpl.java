package com.github.he305.contentcore.streamlist.application.mapper;

import com.github.he305.contentcore.streamlist.application.dto.StreamListDto;
import com.github.he305.contentcore.streamlist.application.dto.StreamListEntryDto;
import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StreamListDtoMapperImpl implements StreamListDtoMapper {
    private final StreamListEntryDtoMapper streamListEntryDtoMapper;

    @Override
    public StreamListDto toDto(StreamList data) {
        List<StreamListEntryDto> streamListEntryDtos = data.getStreamChannelIdList().stream()
                .map(streamListEntryDtoMapper::toDto)
                .collect(Collectors.toList());

        return new StreamListDto(streamListEntryDtos);
    }
}
