package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListContentAccountMapperImpl implements ListContentAccountMapper {
    private final ContentAccountDtoIdMapper contentAccountDtoIdMapper;

    @Override
    public Set<ContentAccountEntry> toContentAccountEntry(List<ContentAccountDto> list) {
        return list.stream()
                .map(contentAccountDtoIdMapper::toContentAccountEntry)
                .collect(Collectors.toSet());
    }

    @Override
    public List<ContentAccountDto> toContentAccountDtoList(Set<ContentAccountEntry> set) {
        return set.stream()
                .map(contentAccountDtoIdMapper::toContentAccountDto)
                .collect(Collectors.toList());
    }
}
