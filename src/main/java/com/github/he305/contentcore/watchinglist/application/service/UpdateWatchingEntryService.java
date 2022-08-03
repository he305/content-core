package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.UpdateWatchingEntryCommand;

public interface UpdateWatchingEntryService {
    void updateWatchingEntry(UpdateWatchingEntryCommand command);
}
