package com.github.he305.contentcore.watchinglist.application.mapper.query;

import com.github.he305.contentcore.watchinglist.application.dto.query.GetContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;

public interface GetContentAccountDtoMapper {
    GetContentAccountDto toDto(ContentAccountEntry entry);
}
