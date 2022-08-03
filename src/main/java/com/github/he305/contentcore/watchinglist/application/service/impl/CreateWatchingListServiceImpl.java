package com.github.he305.contentcore.watchinglist.application.service.impl;

import com.github.he305.contentcore.watchinglist.application.commands.CreateWatchingListCommand;
import com.github.he305.contentcore.watchinglist.application.service.CreateWatchingListService;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import com.github.he305.contentcore.watchinglist.domain.service.ContentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateWatchingListServiceImpl implements CreateWatchingListService {
    private final ContentAccountService contentAccountService;
    private final WatchingListRepository watchingListRepository;

    @Override
    public void execute(CreateWatchingListCommand command) {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), command.getMemberId());
        command.getWatchingList().forEach(watchingListEntryDto -> {
            Set<ContentAccountId> contentAccountIds = watchingListEntryDto
                    .getAccounts()
                    .stream()
                    .map(account -> contentAccountService.getContentAccountId(new ContentAccount(account.getName(), account.getPlatform())))
                    .collect(Collectors.toSet());
            watchingList.addWatchingListEntry(watchingListEntryDto.getName(), contentAccountIds);
        });

        watchingListRepository.save(watchingList);
    }
}
