package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.AddWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.mapper.ListContentAccountMapper;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddWatchingEntryServiceImpl implements AddWatchingEntryService {
    private final WatchingListRepository watchingListRepository;
    private final ListContentAccountMapper listContentAccountMapper;

    @Override
    public void addWatchingEntry(AddWatchingEntryCommand command) {
        MemberId memberId = new MemberId(command.getMemberId());
        Optional<WatchingList> optionalWatchingList = watchingListRepository.getWatchingListByMemberId(memberId);

        WatchingList watchingList;
        if (optionalWatchingList.isEmpty()) {
            watchingList = new WatchingList(UUID.randomUUID(), memberId.getId());
        } else {
            watchingList = optionalWatchingList.get();
        }

        String name = command.getDto().getName();
        Set<ContentAccountEntry> contentAccountEntries = listContentAccountMapper.toContentAccountEntry(command.getDto().getAccounts());

        watchingList.addWatchingListEntry(name, contentAccountEntries);
        watchingListRepository.save(watchingList);
    }
}
