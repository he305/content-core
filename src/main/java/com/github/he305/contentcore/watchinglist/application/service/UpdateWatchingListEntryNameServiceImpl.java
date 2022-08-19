package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.UpdateWatchingListEntryNameCommand;
import com.github.he305.contentcore.watchinglist.application.exceptions.UpdateWatchingListEntryEqualNamesException;
import com.github.he305.contentcore.watchinglist.application.exceptions.WatchingListNotExistsException;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateWatchingListEntryNameServiceImpl implements UpdateWatchingListEntryNameService {
    private final WatchingListRepository watchingListRepository;

    @Override
    public void execute(UpdateWatchingListEntryNameCommand command) {
        if (command.getOldName().equals(command.getNewName())) {
            throw new UpdateWatchingListEntryEqualNamesException(command.getOldName(), command.getNewName());
        }

        MemberId memberId = new MemberId(command.getMemberId());
        Optional<WatchingList> optionalWatchingList = watchingListRepository.getWatchingListByMemberId(memberId);
        if (optionalWatchingList.isEmpty()) {
            throw new WatchingListNotExistsException();
        }

        WatchingList watchingList = optionalWatchingList.get();
        watchingList.updateWatchingListEntryName(command.getOldName(), command.getNewName());
        watchingListRepository.save(watchingList);
    }
}
