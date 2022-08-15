package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;

public interface ContentAccountDtoIdMapper {
    ContentAccountEntry toContentAccountEntry(ContentAccountDto dto);

    ContentAccountDto toContentAccountDto(ContentAccountEntry entry);
}
