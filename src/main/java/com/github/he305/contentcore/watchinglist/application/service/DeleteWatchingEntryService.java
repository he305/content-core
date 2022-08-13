package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.DeleteWatchingEntryCommand;

public interface DeleteWatchingEntryService {
    void execute(DeleteWatchingEntryCommand command);
}
