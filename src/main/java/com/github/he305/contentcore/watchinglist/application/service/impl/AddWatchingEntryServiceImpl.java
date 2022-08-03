package com.github.he305.contentcore.watchinglist.application.service.impl;

import com.github.he305.contentcore.watchinglist.application.commands.AddWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.service.AddWatchingEntryService;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import com.github.he305.contentcore.watchinglist.domain.service.ContentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddWatchingEntryServiceImpl implements AddWatchingEntryService {
    private final WatchingListRepository watchingListRepository;
    private final ContentAccountService contentAccountService;

    @Override
    public void addWatchingEntry(AddWatchingEntryCommand command) {
        MemberId memberId = new MemberId(command.getMemberId());
        Optional<WatchingList> optionalWatchingList = watchingListRepository.getWatchingListByMemberId(memberId);
        if (optionalWatchingList.isEmpty()) {
            throw new IllegalStateException();
        }

        WatchingList watchingList = optionalWatchingList.get();
        String name = command.getDto().getName();
        Set<ContentAccountId> contentAccountIdSet = command
                .getDto()
                .getAccounts()
                .stream()
                .map(contentAccountDto -> new ContentAccount(contentAccountDto.getName(), contentAccountDto.getPlatform()))
                .map(contentAccountService::getContentAccountId)
                .collect(Collectors.toSet());

        watchingList.addWatchingListEntry(name, contentAccountIdSet);
        watchingListRepository.save(watchingList);
    }
}
