package com.github.he305.contentcore.streamlist.application.mapper;

import com.github.he305.contentcore.streamlist.application.dto.StreamListDto;
import com.github.he305.contentcore.streamlist.domain.model.StreamList;

public interface StreamListDtoMapper {
    StreamListDto toDto(StreamList data);
}
