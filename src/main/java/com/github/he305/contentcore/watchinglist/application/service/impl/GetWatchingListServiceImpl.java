package com.github.he305.contentcore.watchinglist.application.service.impl;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.MemberIdDto;
import com.github.he305.contentcore.watchinglist.application.dto.WatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.application.query.GetWatchingListQuery;
import com.github.he305.contentcore.watchinglist.application.service.GetWatchingListService;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import com.github.he305.contentcore.watchinglist.domain.service.ContentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetWatchingListServiceImpl implements GetWatchingListService {
    private final ContentAccountService contentAccountService;
    private final WatchingListRepository watchingListRepository;

    @Override
    public GetWatchingListQuery getWatchingList(MemberIdDto dto) {
        MemberId memberId = new MemberId(dto.getMemberId());
        Optional<WatchingList> watchingListOptional = watchingListRepository.getWatchingListByMemberId(memberId);
        if (watchingListOptional.isEmpty()) {
            return null;
        }

        List<WatchingListEntryDto> watchingListEntryDtos = watchingListOptional.get()
                .getWatchingListEntries()
                .stream()
                .map(entry -> {
                    List<ContentAccountDto> contentAccountDtos = entry
                            .getContentAccountSet()
                            .stream()
                            .map(contentAccountService::getContentAccount)
                            .map(contentAccount -> new ContentAccountDto(contentAccount.getName(), contentAccount.getPlatform()))
                            .collect(Collectors.toList());
                    return new WatchingListEntryDto(entry.getContentCreatorName(), contentAccountDtos);
                }).collect(Collectors.toList());

        return new GetWatchingListQuery(
                memberId.getId(),
                watchingListEntryDtos
        );
    }
}
