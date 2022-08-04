package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.commands.CreateWatchingListCommand;

public interface CreateWatchingListService {
    void execute(CreateWatchingListCommand command);
}
