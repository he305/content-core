package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.DeleteWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.exceptions.WatchingListNotExistsException;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteWatchingEntryServiceImpl implements DeleteWatchingEntryService {
    private final WatchingListRepository watchingListRepository;

    @Override
    public void execute(DeleteWatchingEntryCommand command) {
        MemberId memberId = new MemberId(command.getMemberId());
        WatchingList watchingList = watchingListRepository.getWatchingListByMemberId(memberId).orElseThrow(WatchingListNotExistsException::new);

        watchingList.deleteWatchingListEntry(command.getEntryName());
        watchingListRepository.save(watchingList);
    }
}
