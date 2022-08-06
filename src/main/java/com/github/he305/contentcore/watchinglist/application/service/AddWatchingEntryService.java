package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.AddWatchingEntryCommand;

public interface AddWatchingEntryService {
    void addWatchingEntry(AddWatchingEntryCommand command);
}
