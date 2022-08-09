package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.dto.MemberIdDto;
import com.github.he305.contentcore.watchinglist.application.dto.query.GetWatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.application.mapper.query.GetWatchingListEntryMapper;
import com.github.he305.contentcore.watchinglist.application.query.GetWatchingListQuery;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetWatchingListServiceImpl implements GetWatchingListService {
    private final GetWatchingListEntryMapper getWatchingListEntryMapper;
    private final WatchingListRepository watchingListRepository;

    @Override
    public GetWatchingListQuery getWatchingList(MemberIdDto dto) {
        MemberId memberId = new MemberId(dto.getMemberId());
        Optional<WatchingList> watchingListOptional = watchingListRepository.getWatchingListByMemberId(memberId);
        if (watchingListOptional.isEmpty()) {
            return new GetWatchingListQuery(Collections.emptyList());
        }

        List<GetWatchingListEntryDto> watchingListEntryDtos = watchingListOptional.get()
                .getWatchingListEntries()
                .stream()
                .map(getWatchingListEntryMapper::toDto).collect(Collectors.toList());

        return new GetWatchingListQuery(
                watchingListEntryDtos
        );
    }
}
