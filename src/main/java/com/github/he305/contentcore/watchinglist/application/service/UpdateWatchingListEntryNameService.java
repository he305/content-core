package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.UpdateWatchingListEntryNameCommand;

public interface UpdateWatchingListEntryNameService {
    void execute(UpdateWatchingListEntryNameCommand command);
}
