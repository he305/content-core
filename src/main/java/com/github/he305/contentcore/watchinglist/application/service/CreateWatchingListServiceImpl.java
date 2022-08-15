package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.CreateWatchingListCommand;
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
public class CreateWatchingListServiceImpl implements CreateWatchingListService {
    private final ListContentAccountMapper listContentAccountMapper;
    private final WatchingListRepository watchingListRepository;

    @Override
    public void execute(CreateWatchingListCommand command) {
        if (command.getWatchingList().isEmpty()) {
            throw new IllegalArgumentException();
        }

        MemberId memberId = new MemberId(command.getMemberId());
        Optional<WatchingList> optionalWatchingList = watchingListRepository.getWatchingListByMemberId(memberId);
        if (optionalWatchingList.isPresent()) {
            throw new IllegalArgumentException();
        }

        WatchingList watchingList = new WatchingList(UUID.randomUUID(), command.getMemberId());
        command.getWatchingList().forEach(watchingListEntryDto -> {
            Set<ContentAccountEntry> contentAccountEntries = listContentAccountMapper.toContentAccountEntry(watchingListEntryDto.getAccounts());
            watchingList.addWatchingListEntry(watchingListEntryDto.getName(), contentAccountEntries);
        });

        watchingListRepository.save(watchingList);
    }
}
