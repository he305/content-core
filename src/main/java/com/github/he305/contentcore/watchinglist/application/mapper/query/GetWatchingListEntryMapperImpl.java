package com.github.he305.contentcore.watchinglist.application.mapper.query;

import com.github.he305.contentcore.watchinglist.application.dto.query.GetContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.query.GetWatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetWatchingListEntryMapperImpl implements GetWatchingListEntryMapper {

    private final GetContentAccountDtoMapper getContentAccountDtoMapper;

    @Override
    public GetWatchingListEntryDto toDto(WatchingListEntry entry) {
        List<GetContentAccountDto> contentAccountDtos = entry.getContentAccountSet()
                .stream()
                .map(getContentAccountDtoMapper::toDto)
                .collect(Collectors.toList());
        return new GetWatchingListEntryDto(
                entry.getContentCreatorName(),
                contentAccountDtos
        );
    }
}
