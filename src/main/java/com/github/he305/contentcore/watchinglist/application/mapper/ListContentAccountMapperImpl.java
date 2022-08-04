package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
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
    public Set<ContentAccountId> toContentAccountIdSet(List<ContentAccountDto> list) {
        return list.stream()
                .map(contentAccountDtoIdMapper::toContentAccountId)
                .collect(Collectors.toSet());
    }

    @Override
    public List<ContentAccountDto> toContentAccountDtoList(Set<ContentAccountId> set) {
        return set.stream()
                .map(contentAccountDtoIdMapper::toContentAccountDto)
                .collect(Collectors.toList());
    }
}
