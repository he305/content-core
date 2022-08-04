package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;

public interface ContentAccountDtoIdMapper {
    ContentAccountId toContentAccountId(ContentAccountDto dto);

    ContentAccountDto toContentAccountDto(ContentAccountId id);
}
