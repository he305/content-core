package com.github.he305.contentcore.streamlist.application.mapper;

import com.github.he305.contentcore.streamlist.application.dto.StreamListEntryDto;
import com.github.he305.contentcore.streamlist.domain.model.values.StreamChannelId;

public interface StreamListEntryDtoMapper {
    StreamListEntryDto toDto(StreamChannelId id);
}
