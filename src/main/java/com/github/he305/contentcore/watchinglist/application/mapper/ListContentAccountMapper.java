package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;

import java.util.List;
import java.util.Set;

public interface ListContentAccountMapper {
    Set<ContentAccountEntry> toContentAccountEntry(List<ContentAccountDto> list);

    List<ContentAccountDto> toContentAccountDtoList(Set<ContentAccountEntry> set);
}
