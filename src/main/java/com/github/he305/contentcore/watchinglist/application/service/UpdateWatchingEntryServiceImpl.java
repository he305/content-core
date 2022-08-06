package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.UpdateWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.mapper.ListContentAccountMapper;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UpdateWatchingEntryServiceImpl implements UpdateWatchingEntryService {

    private final WatchingListRepository watchingListRepository;
    private final ListContentAccountMapper listContentAccountMapper;

    @Override
    public void updateWatchingEntry(UpdateWatchingEntryCommand command) {
        MemberId memberId = new MemberId(command.getMemberId());
        Optional<WatchingList> optionalWatchingList = watchingListRepository.getWatchingListByMemberId(memberId);
        if (optionalWatchingList.isEmpty()) {
            throw new IllegalStateException();
        }

        WatchingList watchingList = optionalWatchingList.get();
        String name = command.getDto().getName();
        Set<ContentAccountId> contentAccountIdSet = listContentAccountMapper.toContentAccountIdSet(command.getDto().getAccounts());

        watchingList.updateWatchingListEntry(name, contentAccountIdSet);
        watchingListRepository.save(watchingList);
    }
}
