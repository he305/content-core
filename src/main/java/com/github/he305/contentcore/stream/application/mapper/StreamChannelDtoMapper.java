package com.github.he305.contentcore.stream.application.mapper;

import com.github.he305.contentcore.stream.application.dto.StreamChannelDto;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;

public interface StreamChannelDtoMapper {
    StreamChannelDto toDto(StreamChannel streamChannel);
}
